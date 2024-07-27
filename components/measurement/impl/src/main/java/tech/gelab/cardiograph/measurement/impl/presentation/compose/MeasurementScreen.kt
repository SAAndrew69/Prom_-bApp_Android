package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.measurement.api.BluetoothQuality
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementAction
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementEvent
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementScreenState
import tech.gelab.cardiograph.measurement.impl.presentation.MeasurementViewModel
import tech.gelab.cardiograph.ui.cardiogram.CardiogramList
import tech.gelab.cardiograph.ui.cardiogram.MultipleCardiogramValueProducer
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar

@Composable
fun MeasurementScreen(viewModel: MeasurementViewModel = hiltViewModel()) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(initial = null)

    MeasurementView(
        modifier = Modifier.fillMaxSize(),
        multipleCardiogramValueProducer = viewModel.multipleCardiogramValueProducer,
        viewState = viewState,
        viewAction = viewAction,
        onEvent = viewModel::obtainEvent
    )
}

@Composable
fun MeasurementView(
    modifier: Modifier = Modifier,
    multipleCardiogramValueProducer: MultipleCardiogramValueProducer,
    viewState: MeasurementScreenState,
    viewAction: MeasurementAction?,
    onEvent: (MeasurementEvent) -> Unit
) {
    Column(modifier) {
        CardioAppBar(
            topBarState = viewState.topBarState,
            onBackButtonClick = { onEvent(MeasurementEvent.BackButtonClick) },
            actions = {
                IconButton(onClick = { onEvent(MeasurementEvent.InfoButtonClick) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_info),
                        contentDescription = null
                    )
                }
            }
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                text = viewState.supportingText,
                style = MaterialTheme.typography.labelLarge
            )
            CardiogramList(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.small),
                multipleCardiogramValueProducer = multipleCardiogramValueProducer
            )
            if (viewState.bottomSheetState != null) {
                MeasurementBottomSheet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(380.dp)
                        .padding(MaterialTheme.spacing.small),
                    viewState = viewState,
                    onStartAgain = { onEvent(MeasurementEvent.StartAgainClick) },
                    onExerciseComplete = { onEvent(MeasurementEvent.ExerciseCompleteClick) },
                    onStartSecondMeasure = { onEvent(MeasurementEvent.StartSecondMeasureClick) }
                )
            } else {
                CardioButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.small),
                    text = stringResource(id = R.string.label_start_measure),
                    onClick = { onEvent(MeasurementEvent.StartMeasure) }
                )
            }

        }
    }
}

@Composable
fun MeasurementBottomSheet(
    modifier: Modifier = Modifier,
    viewState: MeasurementScreenState,
    onStartAgain: () -> Unit,
    onExerciseComplete: () -> Unit,
    onStartSecondMeasure: () -> Unit
) {
    AnimatedVisibility(visible = viewState.bottomSheetState != null) {
        Surface(
            shadowElevation = 50.dp,
            shape = MaterialTheme.shapes.extraLarge.copy(
                bottomStart = CornerSize(0.dp),
                bottomEnd = CornerSize(0.dp)
            )
        ) {
            when (viewState.bottomSheetState) {
                BottomSheetState.DataUpload -> DataUploadBottomSheet(
                    modifier = modifier,
                    onStartAgainClick = onStartAgain
                )

                is BottomSheetState.MeasureInProgress -> MeasurementBottomSheet(
                    modifier = modifier,
                    state = viewState.bottomSheetState,
                    onStartAgainClick = onStartAgain
                )

                BottomSheetState.MeasureSuccess -> SuccessBottomSheet(modifier = modifier)

                is BottomSheetState.SecondMeasurePreparation -> SecondMeasurePrepBottomSheet(
                    modifier = modifier,
                    state = viewState.bottomSheetState,
                    onSecondMeasureClick = onStartSecondMeasure,
                    onStartAgainClick = onStartAgain
                )

                BottomSheetState.UploadSuccess -> ExerciseBottomSheet(
                    modifier = modifier,
                    onExerciseComplete = onExerciseComplete,
                    onStartAgainClick = onStartAgain
                )

                null -> {}
            }
        }
    }
}