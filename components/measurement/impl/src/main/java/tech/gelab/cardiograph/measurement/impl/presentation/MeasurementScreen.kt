package tech.gelab.cardiograph.measurement.impl.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementAction
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementEvent
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementState
import tech.gelab.cardiograph.measurement.impl.model.BluetoothQuality
import tech.gelab.cardiograph.ui.ktx.element.CardioAppOutlinedButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState

@Composable
fun MeasurementScreen(viewModel: MeasurementViewModel) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(initial = null)

    MeasurementView(
        modifier = Modifier.fillMaxSize(),
        viewState = viewState,
        viewAction = viewAction,
        onEvent = viewModel::obtainEvent
    )
}

@Composable
fun MeasurementView(
    modifier: Modifier = Modifier,
    viewState: MeasurementState,
    viewAction: MeasurementAction?,
    onEvent: (MeasurementEvent) -> Unit
) {
    Column(modifier) {
        CardioAppBar(topBarState = TopBarState(R.string.title_measure))
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                text = stringResource(R.string.text_please_wait),
                style = MaterialTheme.typography.labelLarge
            )
            MeasurementBottomSheet(
                viewState = viewState,
                onStartAgain = { onEvent(MeasurementEvent.StartAgainClick) })
        }
    }
}

@Composable
fun MeasurementBottomSheet(
    modifier: Modifier = Modifier,
    viewState: MeasurementState,
    onStartAgain: () -> Unit
) {
    Surface(
        modifier.padding(MaterialTheme.spacing.small)
    ) {
        Column(
            Modifier.padding(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.label_bluetooth_quality),
                style = MaterialTheme.typography.labelMedium
            )
            BluetoothQualityText(bluetoothQuality = viewState.bluetoothQuality)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedText(
                    label = stringResource(id = R.string.label_employee),
                    text = viewState.employeeId
                )
                OutlinedText(
                    label = stringResource(id = R.string.label_measurement_num),
                    text = viewState.measurementString
                )
            }
            ProgressIndicator(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium).height(120.dp),
                progress = viewState.progress,
                timeString = viewState.timeString
            )
            CardioAppOutlinedButton(
                modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.spacing.large),
                leadingIconRes = R.drawable.icon_start_again,
                labelId = R.string.label_start_again,
                onClick = onStartAgain)
        }
    }
}

@Composable
fun BluetoothQualityText(modifier: Modifier = Modifier, bluetoothQuality: BluetoothQuality) {
    when (bluetoothQuality) {
        BluetoothQuality.GOOD -> Text(
            modifier = modifier,
            text = stringResource(R.string.text_quality_good),
            style = MaterialTheme.typography.labelMedium.copy(
                color = Color(0xFF0C7817),
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun OutlinedText(modifier: Modifier = Modifier, label: String, text: String) {
    Column(modifier) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Box(
            Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .padding(MaterialTheme.spacing.extraSmall)
                .width(100.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier, progress: Float, timeString: String) {
    val animatedProgress by animateFloatAsState(targetValue = progress)
    Box(modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.outline,
            strokeCap = StrokeCap.Round,
            strokeWidth = 8.dp,
            progress = { animatedProgress }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = timeString,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview
@Composable
private fun MeasurementViewPrev() {

    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            MeasurementView(
                modifier = Modifier.fillMaxSize(),
                viewState = MeasurementState(progress = 0.6f),
                viewAction = null
            ) {

            }
        }
    }
}