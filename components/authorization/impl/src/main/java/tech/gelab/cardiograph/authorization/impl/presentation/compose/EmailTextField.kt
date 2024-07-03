package tech.gelab.cardiograph.authorization.impl.presentation.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PlatformImeOptions
import tech.gelab.cardiograph.authorization.impl.R

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    email: String,
    updateState: (String) -> Unit,
    hasErrors: Boolean,
    onNext: () -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = email,
        onValueChange = updateState,
        label = { Text(stringResource(id = R.string.label_email)) },
        isError = hasErrors,
        supportingText = {
            if (hasErrors) {
                Text(text = stringResource(id = R.string.text_incorrect_email))
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions()
    )
}