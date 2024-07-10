package tech.gelab.cardiograph.ui.ktx.element

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.spacing


@Composable
fun CardioOutlinedButton(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int? = null,
    @StringRes labelId: Int,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.extraSmall,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        if (leadingIconRes != null) {
            Icon(
                painter = painterResource(id = leadingIconRes),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
        }
        Text(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
            text = stringResource(id = labelId),
            style = textStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


