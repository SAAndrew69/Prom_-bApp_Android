package tech.gelab.cardiograph.ui.ktx.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun CardioAppOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    supportingText: String? = null,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Column(modifier) {
        if (labelText != null) {
            Text(
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraSmall),
                text = labelText,
                style = MaterialTheme.typography.labelSmall
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder.letCompose {
                Text(text = it)
            },
            isError = isError,
            supportingText = supportingText.letCompose {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            enabled = enabled,
            textStyle = textStyle,
            trailingIcon = trailingIcon,
            shape = MaterialTheme.shapes.small
        )
    }
}

@Preview
@Composable
private fun CardioAppOutlinedTextFieldPrev() {
    CardiographAppTheme {
        Box(
            Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.small)
        ) {
            CardioAppOutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder"
            )
        }
    }
}

@Preview
@Composable
private fun CardioAppOutlinedTextFieldInputPrev() {
    CardiographAppTheme {
        Box(
            Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.small)
        ) {
            CardioAppOutlinedTextField(
                value = "Text",
                onValueChange = {},
                labelText = "Placeholder",
            )
        }
    }
}

@Preview
@Composable
private fun CardioAppOutlinedTextFieldErrorPrev() {
    CardiographAppTheme {
        Box(
            Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.small)
        ) {
            CardioAppOutlinedTextField(
                value = "Text",
                onValueChange = {},
                labelText = "Placeholder",
                isError = true,
                supportingText = "Error text"
            )
        }
    }
}