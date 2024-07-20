package tech.gelab.cardiograph.measurement.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun MeasurementBottomSheet(
    modifier: Modifier = Modifier,
    state: BottomSheetState.MeasureInProgress,
    onStartAgainClick: () -> Unit
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.label_bluetooth_quality),
            style = MaterialTheme.typography.labelMedium
        )
        BluetoothQualityText(bluetoothQuality = state.bluetoothQuality)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedText(
                label = stringResource(id = R.string.label_employee),
                text = state.employeeId
            )
            OutlinedText(
                label = stringResource(id = R.string.label_measurement_num),
                text = state.measurementNumLabel
            )
        }
        ProgressIndicator(
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.medium)
                .height(120.dp),
            progress = state.progress,
            timeLabel = state.progressLabel
        )
        StartAgainButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.large),
            onClick = onStartAgainClick
        )
    }
}