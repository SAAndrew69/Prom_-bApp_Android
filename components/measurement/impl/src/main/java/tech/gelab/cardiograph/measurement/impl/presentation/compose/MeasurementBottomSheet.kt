package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.measurement.api.BluetoothQuality
import tech.gelab.cardiograph.measurement.impl.MeasurementConst
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun MeasurementBottomSheet(
    modifier: Modifier = Modifier,
    state: BottomSheetState.MeasureInProgress,
    onStartAgainClick: () -> Unit
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.label_bluetooth_quality),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
            BluetoothQualityText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.extraSmall),
                bluetoothQuality = state.bluetoothQuality
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.extraSmall),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedText(
                    label = stringResource(id = R.string.label_employee),
                    text = state.employeeId
                )
                OutlinedText(
                    label = stringResource(id = R.string.label_measurement_num),
                    text = state.measurementNumLabel
                )
            }
        }
        ProgressIndicator(
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.small)
                .height(MeasurementConst.PROGRESS_INDICATOR_HEIGHT.dp),
            progress = state.progress,
            timeLabel = state.progressLabel
        )
        RestartButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.large),
            onClick = onStartAgainClick
        )
    }
}

@Preview
@Composable
private fun MeasurementBottomSheetPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {

            MeasurementBottomSheet(
                state = BottomSheetState.MeasureInProgress(
                    bluetoothQuality = BluetoothQuality.GOOD,
                    employeeId = "1234",
                    measurementNumLabel = "Первое",
                    progressLabel = "1:00",
                    progress = 0.5f
                )
            ) {
            }

        }
    }
}