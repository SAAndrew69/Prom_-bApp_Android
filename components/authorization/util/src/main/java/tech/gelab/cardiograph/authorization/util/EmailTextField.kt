package tech.gelab.cardiograph.authorization.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import tech.gelab.cardiograph.ui.ktx.element.CardioOutlinedTextField

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    email: String,
    enabled: Boolean,
    updateState: (String) -> Unit,
    hasErrors: Boolean,
    onNext: KeyboardActionScope.() -> Unit,
) {
    CardioOutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = email,
        enabled = enabled,
        onValueChange = updateState,
        labelText = stringResource(R.string.label_email),
        placeholder = stringResource(R.string.text_placeholder_email),
        isError = hasErrors,
        supportingText = if (hasErrors) stringResource(id = R.string.text_incorrect_email) else null,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext)
    )
}