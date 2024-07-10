package tech.gelab.cardiograph.progressnavigation.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import tech.gelab.cardiograph.measurement.progressnavigation.R
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun SignalQualityScreen(onNextClick: () -> Unit) {
    SignalQualityView(
        Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.small), onNextClick)
}

@Composable
fun SignalQualityView(modifier: Modifier = Modifier, onNextClick: () -> Unit) {
    Column(
        modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.text_signal_quality),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        CardioButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_start_measure),
            onClick = onNextClick
        )
    }
}