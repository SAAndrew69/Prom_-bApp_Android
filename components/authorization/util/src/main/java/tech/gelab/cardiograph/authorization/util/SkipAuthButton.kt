package tech.gelab.cardiograph.authorization.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.gelab.cardiograph.ui.ktx.element.CardioTextButton

@Composable
fun SkipAuthButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    CardioTextButton(
        modifier = modifier,
        text = stringResource(R.string.label_skip_authorization),
        onClick = onClick
    )
}