package tech.gelab.cardiograph.authorization.login.presentation

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.authorization.login.LoginFeatureEvent
import tech.gelab.cardiograph.authorization.login.R
import tech.gelab.cardiograph.authorization.login.domain.LoginAction
import tech.gelab.cardiograph.authorization.login.domain.LoginEvent
import tech.gelab.cardiograph.authorization.login.domain.LoginScreenState
import tech.gelab.cardiograph.authorization.util.validateEmail
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginFeatureEventHandler: FeatureEventHandler<LoginFeatureEvent>,
    private val authService: AuthService,
    private val resourceProvider: ResourceProvider,
    private val toastHelper: ToastHelper
) : BaseViewModel<LoginScreenState, LoginAction, LoginEvent>(LoginScreenState()) {

    override fun obtainEvent(viewEvent: LoginEvent) {
        when (viewEvent) {
            is LoginEvent.EmailUpdate -> onEmailUpdate(viewEvent)
            LoginEvent.EmailSubmit -> onEmailSubmit()
            is LoginEvent.PasswordUpdate -> onPasswordUpdate(viewEvent)
            LoginEvent.PasswordSubmit -> onPasswordSubmit()
            LoginEvent.VisibilityClick -> onVisibilityClick()
            LoginEvent.LoginClick -> onLoginClick()
            LoginEvent.SkipClick -> loginFeatureEventHandler.obtainEvent(LoginFeatureEvent.Skip)
            LoginEvent.ForgotPasswordClick -> {}
            LoginEvent.BackClick -> loginFeatureEventHandler.obtainEvent(LoginFeatureEvent.PopBackStack)
        }
    }

    private fun onEmailUpdate(event: LoginEvent.EmailUpdate) {
        viewState = viewState.copy(
            email = event.value,
            emailError = if (viewState.emailError && validateEmail(event.value)) false else viewState.emailError
        )
    }

    private fun onEmailSubmit() {
        viewState = viewState.copy(emailError = !validateEmail(viewState.email))
    }

    private fun onPasswordUpdate(event: LoginEvent.PasswordUpdate) {
        viewState = viewState.copy(password = event.value)
    }

    private fun onPasswordSubmit() {
        onLoginClick()
    }

    private fun onVisibilityClick() {
        viewState = viewState.copy(passwordVisible = viewState.passwordVisible.not())
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            if (!validateEmail(viewState.email)) {
                viewState = viewState.copy(emailError = true)
                toastHelper.showToast(
                    tech.gelab.cardiograph.authorization.util.R.string.text_incorrect_email,
                    Toast.LENGTH_SHORT
                )
                return@launch
            }

            authService.authorize(viewState.email, viewState.password)
                .onSuccess { token ->
                    loginFeatureEventHandler.obtainEvent(
                        LoginFeatureEvent.LoginSuccess(
                            token
                        )
                    )
                }
                .onFailure {
                    loginFeatureEventHandler.obtainEvent(
                        LoginFeatureEvent.LoginFailure(
                            it.message ?: resourceProvider.getString(R.string.text_auth_error)
                        )
                    )
                }
        }
    }
}