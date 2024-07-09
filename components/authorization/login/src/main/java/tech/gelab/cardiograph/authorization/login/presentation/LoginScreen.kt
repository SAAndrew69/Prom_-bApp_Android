package tech.gelab.cardiograph.authorization.login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.authorization.login.R
import tech.gelab.cardiograph.authorization.login.domain.LoginAction
import tech.gelab.cardiograph.authorization.login.domain.LoginEvent
import tech.gelab.cardiograph.authorization.login.domain.LoginScreenState
import tech.gelab.cardiograph.authorization.util.EmailTextField
import tech.gelab.cardiograph.authorization.util.PasswordTextField
import tech.gelab.cardiograph.ui.ktx.element.CardioAppButton
import tech.gelab.cardiograph.ui.ktx.element.CardioAppTextButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    Column(Modifier.fillMaxSize()) {

        val viewState by viewModel.viewStates().collectAsState()
        val viewAction by viewModel.viewActions().collectAsState(initial = null)

        LoginView(
            Modifier.fillMaxSize(),
            viewState = viewState,
            viewAction = viewAction,
            onEvent = viewModel::obtainEvent
        )
    }
}

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    viewState: LoginScreenState,
    viewAction: LoginAction?,
    onEvent: (LoginEvent) -> Unit,
) {
    Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
        CardioAppBar(
            modifier = Modifier.fillMaxWidth(),
            topBarState = TopBarState(titleId = R.string.title_login, showBackButton = true),
            onBackButtonClick = { onEvent(LoginEvent.BackClick) }
        )
        LoginInputsView(
            viewState = viewState,
            onEvent = onEvent
        )
        LoginButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.medium
                ),
            onEvent = onEvent
        )
    }
}

@Composable
fun LoginInputsView(
    modifier: Modifier = Modifier,
    viewState: LoginScreenState,
    onEvent: (LoginEvent) -> Unit,
) {
    Column(modifier, horizontalAlignment = Alignment.End) {
        EmailTextField(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small).onFocusChanged {
                if (!it.isFocused && viewState.email.isNotEmpty()) {
                    onEvent(LoginEvent.EmailSubmit)
                }
            },
            email = viewState.email,
            hasErrors = viewState.emailError,
            enabled = !viewState.signingIn,
            updateState = { onEvent(LoginEvent.EmailUpdate(it)) },
            // TODO make ktx
            onNext = {
                onEvent(LoginEvent.EmailSubmit)
                defaultKeyboardAction(ImeAction.Next)
            }
        )
        PasswordTextField(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.extraSmall
            ),
            password = viewState.password,
            enabled = !viewState.signingIn,
            visible = viewState.passwordVisible,
            placeholderResource = R.string.text_password,
            updateInput = { onEvent(LoginEvent.PasswordUpdate(it)) },
            onVisibilityIconClick = { onEvent(LoginEvent.VisibilityClick) },
            onDone = {
                onEvent(LoginEvent.PasswordSubmit)
                defaultKeyboardAction(ImeAction.Done)
            }
        )
        ForgotPassword(onEvent = onEvent)
    }
}

@Composable
fun ForgotPassword(modifier: Modifier = Modifier, onEvent: (LoginEvent) -> Unit) {
    Row(modifier) {
        CardioAppTextButton(
            text = stringResource(id = R.string.label_forgot_password),
            onClick = { onEvent(LoginEvent.ForgotPasswordClick) })
    }
}

@Composable
fun LoginButtons(modifier: Modifier = Modifier, onEvent: (LoginEvent) -> Unit) {
    Column(modifier) {
        CardioAppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.label_authorize),
            onClick = { onEvent(LoginEvent.LoginClick) })
        CardioAppTextButton(
            modifier = Modifier.fillMaxWidth().padding(top = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_skip_authorization),
            onClick = { onEvent(LoginEvent.SkipClick) }
        )
    }
}

@Preview
@Composable
private fun LoginScreenPrev() {
    CardiographAppTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LoginView(
                viewState = LoginScreenState(
                    email = "",
                    emailError = true,
                    password = "",
                    passwordVisible = true,
                ),
                viewAction = null,
                onEvent = {}
            )
        }
    }
}