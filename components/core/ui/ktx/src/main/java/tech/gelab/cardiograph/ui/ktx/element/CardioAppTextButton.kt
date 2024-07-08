package tech.gelab.cardiograph.ui.ktx.element

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme

@Composable
fun CardioAppTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    enabled: Boolean = true
) {
    TextButton(modifier = modifier.fillMaxWidth(), onClick = onClick, enabled = enabled) {
        Text(text = text, style = textStyle)
    }
}

@Preview
@Composable
fun CardiographTextButtonPreview() {
    CardiographAppTheme {
        CardioAppTextButton(text = "Авторизоваться", onClick = { /*TODO*/ })
    }
}