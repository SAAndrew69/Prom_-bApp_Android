package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import tech.gelab.cardiograph.measurement.api.BluetoothQuality
import tech.gelab.cardiograph.measurement.impl.R

@Composable
fun BluetoothQualityText(modifier: Modifier = Modifier, bluetoothQuality: BluetoothQuality) {
    when (bluetoothQuality) {
        BluetoothQuality.GOOD -> Text(
            modifier = modifier,
            text = stringResource(R.string.text_quality_good),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(
                color = Color(0xFF0C7817),
                fontWeight = FontWeight.Bold
            )
        )
    }
}