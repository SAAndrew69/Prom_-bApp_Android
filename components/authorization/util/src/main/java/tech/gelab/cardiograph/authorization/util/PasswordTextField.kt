package tech.gelab.cardiograph.authorization.util

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import tech.gelab.cardiograph.ui.ktx.element.CardioOutlinedTextField

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    visible: Boolean,
    enabled: Boolean,
    @StringRes labelResource: Int? = null,
    @StringRes placeholderResource: Int,
    updateInput: (String) -> Unit,
    onVisibilityIconClick: () -> Unit,
    onDone: KeyboardActionScope.() -> Unit,
    errorMessage: String? = null,
) {
    CardioOutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = updateInput,
        placeholder = stringResource(placeholderResource),
        labelText = if (labelResource != null) stringResource(id = labelResource) else null,
        enabled = enabled,
        isError = errorMessage != null,
        supportingText = errorMessage,
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone),
        trailingIcon = {
            val painter = if (visible) painterResource(id = R.drawable.visibility_off)
            else painterResource(id = R.drawable.visibility)

            IconButton(onClick = onVisibilityIconClick) {
                Icon(painter = painter, null)
            }
        }
    )
}