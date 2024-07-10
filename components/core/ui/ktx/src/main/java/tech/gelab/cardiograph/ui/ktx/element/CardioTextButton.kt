package tech.gelab.cardiograph.ui.ktx.element

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme

@Composable
fun CardioTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    textAlign: TextAlign? = null,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    enabled: Boolean = true
) {
    TextButton(modifier = modifier, onClick = onClick, enabled = enabled) {
        Text(text = text, style = textStyle, textAlign = textAlign)
    }
}

@Preview
@Composable
fun CardiographTextButtonPreview() {
    CardiographAppTheme {
        CardioTextButton(text = "Авторизоваться", onClick = { /*TODO*/ })
    }
}