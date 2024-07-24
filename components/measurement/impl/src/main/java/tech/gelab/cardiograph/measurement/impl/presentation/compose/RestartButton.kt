package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.ui.ktx.element.CardioOutlinedButton

@Composable
fun RestartButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    CardioOutlinedButton(
        modifier = modifier,
        leadingIconRes = R.drawable.icon_restart,
        labelId = R.string.label_restart,
        onClick = onClick
    )
}