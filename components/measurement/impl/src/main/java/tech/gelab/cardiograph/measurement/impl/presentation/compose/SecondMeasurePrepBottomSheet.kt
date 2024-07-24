package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.measurement.impl.MeasurementConst
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun SecondMeasurePrepBottomSheet(
    modifier: Modifier = Modifier,
    state: BottomSheetState.SecondMeasurePreparation,
    onSecondMeasureClick: () -> Unit,
    onStartAgainClick: () -> Unit
) {
    Box(modifier) {
        if (state.prepFailed.not()) {
            AwaitClick(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onSecondMeasureClick = onSecondMeasureClick,
                onStartAgainClick = onStartAgainClick
            )
        } else {
            FailedToStart(modifier = Modifier.fillMaxSize(), onStartAgainClick = onStartAgainClick)
        }
    }
}

@Composable
fun AwaitClick(
    modifier: Modifier = Modifier,
    state: BottomSheetState.SecondMeasurePreparation,
    onSecondMeasureClick: () -> Unit,
    onStartAgainClick: () -> Unit
) {
    Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
            text = stringResource(id = R.string.text_relax),
            style = MaterialTheme.typography.labelLarge
        )
        ProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
                .height(MeasurementConst.PROGRESS_INDICATOR_HEIGHT.dp),
            timeLabel = state.progressLabel,
            progress = state.progress,
            indicatorColor = if (state.runningOut) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.primary
        )
        Column {
            CardioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small),
                text = stringResource(id = R.string.label_start_second_measure),
                onClick = onSecondMeasureClick
            )
            RestartButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small),
                onClick = onStartAgainClick
            )
        }
    }
}

@Composable
fun FailedToStart(modifier: Modifier = Modifier, onStartAgainClick: () -> Unit) {
    Column(modifier) {
        Text(text = stringResource(id = R.string.text_runned_out1))
        Text(text = stringResource(id = R.string.text_runned_out2))
        Text(text = stringResource(id = R.string.text_runned_out3))
        RestartButton(modifier = Modifier.fillMaxWidth(), onClick = onStartAgainClick)
    }
}

@Preview
@Composable
private fun SecondMeasurePrepBottomSheetPrev() {
    CardiographAppTheme {
        Box(
            Modifier.background(MaterialTheme.colorScheme.surface)) {
            SecondMeasurePrepBottomSheet(
                state = BottomSheetState.SecondMeasurePreparation(
                    progressLabel = "1:30",
                    progress = 0.75f,
                    runningOut = false
                ), onSecondMeasureClick = {}) {

            }
        }
    }
}