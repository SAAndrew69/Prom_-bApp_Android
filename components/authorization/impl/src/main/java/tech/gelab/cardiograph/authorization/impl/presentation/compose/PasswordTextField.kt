package tech.gelab.cardiograph.authorization.impl.presentation.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.authorization.impl.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    password: String,
    passwordVisible: Boolean,
    updateInput: (String) -> Unit,
    onVisibilityIconClick: () -> Unit,
    onDone: () -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        onValueChange = updateInput,
        label = { Text(stringResource(id = R.string.label_password)) },
        singleLine = true,
        placeholder = { Text(stringResource(R.string.label_password)) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        trailingIcon = {
            val painter = if (passwordVisible) painterResource(id = R.drawable.visibility_off)
            else painterResource(id = R.drawable.visibility)

            IconButton(onClick = onVisibilityIconClick) {
                Icon(painter = painter, null)
            }
        }
    )
}

@Preview
@Composable
fun PasswordTextFieldPreview() {
    PasswordTextField(
        password = "test",
        passwordVisible = false,
        updateInput = {},
        onVisibilityIconClick = { },
        onDone = {})
}