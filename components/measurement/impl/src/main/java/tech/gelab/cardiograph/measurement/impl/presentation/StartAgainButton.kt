package tech.gelab.cardiograph.measurement.impl.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.ui.ktx.element.CardioOutlinedButton
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun StartAgainButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    CardioOutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.large),
        leadingIconRes = R.drawable.icon_start_again,
        labelId = R.string.label_start_again,
        onClick = onClick
    )
}