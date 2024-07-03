package tech.gelab.cardiograph.authorization.impl.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.authorization.impl.R
import tech.gelab.cardiograph.authorization.impl.presentation.viewmodel.AuthorizationViewModel
import tech.gelab.cardiograph.ui.theme.spacing
import timber.log.Timber

@Composable
fun AuthorizationScreen(authorizationViewModel: AuthorizationViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.small),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            text = stringResource(id = R.string.title_authrization),
            style = MaterialTheme.typography.titleLarge
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
                text = stringResource(id = R.string.text_authorize),
                style = MaterialTheme.typography.titleMedium
            )
            EmailTextField(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                email = authorizationViewModel.email,
                updateState = authorizationViewModel::updateEmail,
                hasErrors = authorizationViewModel.emailHasErrors,
                onNext = {}
            )
            PasswordTextField(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                password = authorizationViewModel.password,
                passwordVisible = authorizationViewModel.passwordVisible,
                updateInput = authorizationViewModel::updatePassword,
                onVisibilityIconClick = authorizationViewModel::updatePasswordVisibility,
                onDone = authorizationViewModel::onPasswordSubmit
            )
        }

        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = authorizationViewModel::onAuthorizeClick
            ) {
                Text(text = stringResource(id = R.string.label_authorize))
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = authorizationViewModel::onAuthSkipClick
            ) {
                Text(text = stringResource(id = R.string.label_skip_authorization))
            }
        }
    }
}