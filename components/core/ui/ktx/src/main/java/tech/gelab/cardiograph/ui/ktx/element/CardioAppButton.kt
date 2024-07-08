package tech.gelab.cardiograph.ui.ktx.element

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun CardioAppButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.extraSmall,
        enabled = enabled
    ) {
        // TODO Padding 13???
        Text(modifier = Modifier.padding(vertical = MaterialTheme.spacing.small), text = text, style = textStyle)
    }
}

@Preview(apiLevel = 33)
@Composable
fun CardiographButtonPrev() {
    CardiographAppTheme {
        CardioAppButton(
            modifier = Modifier.width(369.dp),
            text = "Текст",
            onClick = { /*TODO*/ }
        )
    }
}