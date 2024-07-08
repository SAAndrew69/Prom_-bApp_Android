package tech.gelab.cardiograph.authorization.signup.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.authorization.signup.R
import tech.gelab.cardiograph.authorization.signup.domain.SignUpScreenEvent
import tech.gelab.cardiograph.authorization.signup.domain.SignUpScreenState
import tech.gelab.cardiograph.authorization.util.EmailTextField
import tech.gelab.cardiograph.authorization.util.PasswordTextField
import tech.gelab.cardiograph.ui.ktx.element.CardioAppButton
import tech.gelab.cardiograph.ui.ktx.element.CardioAppTextButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    val viewState by viewModel.viewStates().collectAsState()
    SignUpView(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        viewState = viewState,
        onEvent = viewModel::obtainEvent
    )
}

@Composable
fun SignUpView(
    modifier: Modifier = Modifier,
    viewState: SignUpScreenState,
    onEvent: (SignUpScreenEvent) -> Unit,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardioAppBar(
            topBarState = TopBarState(titleId = R.string.title_sign_up, showBackButton = true),
            onBackButtonClick = { onEvent(SignUpScreenEvent.BackClick) }
        )
        SignUpInputs(
            modifier = Modifier.padding(MaterialTheme.spacing.small),
            viewState = viewState,
            onEvent = onEvent
        )
        SignUpButtons(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            viewState = viewState,
            onEvent = onEvent
        )
    }
}

@Composable
fun SignUpInputs(
    modifier: Modifier = Modifier,
    viewState: SignUpScreenState,
    onEvent: (SignUpScreenEvent) -> Unit,
) {
    Column(modifier) {
        EmailTextField(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
            email = viewState.email,
            hasErrors = viewState.emailError,
            enabled = !viewState.signingUp,
            updateState = {
                onEvent(
                    SignUpScreenEvent.TextFieldUpdate(
                        SignUpScreenEvent.TextFieldType.EMAIL,
                        it
                    )
                )
            },
            onNext = {
                onEvent(
                    SignUpScreenEvent.TextFieldSubmit(
                        SignUpScreenEvent.TextFieldType.EMAIL
                    )
                )
            }
        )
        PasswordTextField(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
            password = viewState.password,
            enabled = !viewState.signingUp,
            visible = viewState.passwordVisible,
            labelResource = R.string.label_password,
            placeholderResource = R.string.label_create_password,
            updateInput = {
                onEvent(
                    SignUpScreenEvent.TextFieldUpdate(
                        SignUpScreenEvent.TextFieldType.PASSWORD,
                        it
                    )
                )
            },
            onVisibilityIconClick = { onEvent(SignUpScreenEvent.VisibilityUpdate) },
            onDone = {
                onEvent(
                    SignUpScreenEvent.TextFieldSubmit(
                        SignUpScreenEvent.TextFieldType.PASSWORD
                    )
                )
            }
        )
        PasswordTextField(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
            password = viewState.passwordConfirm,
            enabled = !viewState.signingUp,
            visible = viewState.passwordVisible,
            placeholderResource = R.string.label_confirm_password,
            updateInput = {
                onEvent(
                    SignUpScreenEvent.TextFieldUpdate(
                        SignUpScreenEvent.TextFieldType.PASSWORD_CONFIRM,
                        it
                    )
                )
            },
            onVisibilityIconClick = { onEvent(SignUpScreenEvent.VisibilityUpdate) },
            onDone = {
                onEvent(
                    SignUpScreenEvent.TextFieldSubmit(
                        SignUpScreenEvent.TextFieldType.PASSWORD_CONFIRM
                    )
                )
            }
        )
    }
}

@Composable
fun SignUpButtons(
    modifier: Modifier = Modifier,
    viewState: SignUpScreenState,
    onEvent: (SignUpScreenEvent) -> Unit
) {
    Column(modifier) {
        CardioAppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_register),
            enabled = !viewState.signingUp,
            onClick = { onEvent(SignUpScreenEvent.RegisterClick) }
        )
        CardioAppTextButton(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_skip_registration),
            onClick = { onEvent(SignUpScreenEvent.SkipClick) })
    }
}

@Preview
@Composable
private fun SignInPrev() {
    CardiographAppTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            SignUpView(
                viewState = SignUpScreenState()
            ) {

            }
        }
    }
}