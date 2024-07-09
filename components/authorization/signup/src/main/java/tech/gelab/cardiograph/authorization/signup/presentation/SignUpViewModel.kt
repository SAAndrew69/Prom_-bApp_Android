package tech.gelab.cardiograph.authorization.signup.presentation

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.authorization.signup.R
import tech.gelab.cardiograph.authorization.signup.SignUpFeatureEvent
import tech.gelab.cardiograph.authorization.signup.domain.SignUpAction
import tech.gelab.cardiograph.authorization.signup.domain.SignUpScreenEvent
import tech.gelab.cardiograph.authorization.signup.domain.SignUpScreenState
import tech.gelab.cardiograph.authorization.signup.domain.SignUpUseCase
import tech.gelab.cardiograph.authorization.util.validateEmail
import tech.gelab.cardiograph.authorization.util.validatePassword
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import timber.log.Timber

@HiltViewModel(assistedFactory = SignUpViewModel.Factory::class)
class SignUpViewModel @AssistedInject constructor(
    @Assisted private val signUpFeatureEventHandler: FeatureEventHandler<SignUpFeatureEvent>,
    private val signUpUseCase: SignUpUseCase,
    private val toastHelper: ToastHelper,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel<SignUpScreenState, SignUpAction, SignUpScreenEvent>(SignUpScreenState()) {

    override fun obtainEvent(viewEvent: SignUpScreenEvent) {
        when (viewEvent) {
            SignUpScreenEvent.RegisterClick -> onRegisterClick()
            SignUpScreenEvent.SkipClick -> onSkipClick()
            is SignUpScreenEvent.TextFieldSubmit -> onTextFieldSubmit(viewEvent)
            is SignUpScreenEvent.TextFieldUpdate -> onTextFieldUpdate(viewEvent)
            SignUpScreenEvent.VisibilityUpdate -> onVisibilityUpdate()
            SignUpScreenEvent.BackClick -> onBackClick()
        }
    }

    private fun validateConfirmation(password: String, confirmation: String): Boolean {
        return password.contentEquals(confirmation)
    }

    private fun onRegisterClick() {
        Timber.d("onRegisterClick")
        val email = viewState.email
        if (!validateEmail(email)) {
            toastHelper.showToast(R.string.text_incorrect_email, Toast.LENGTH_SHORT)
            viewState = viewState.copy(emailError = true)
            return
        }
        val password = viewState.password
        val passConfirm = viewState.passwordConfirm

        if (!validatePassword(password)) {
            toastHelper.showToast(R.string.text_incorrect_pass_format, Toast.LENGTH_LONG)
            viewState = viewState.copy(passwordError = true)
            return
        }

        if (!validateConfirmation(password, passConfirm)) {
            toastHelper.showToast(R.string.text_incorrect_pass_confirm, Toast.LENGTH_LONG)
            viewState = viewState.copy(passwordConfirmError = true)
            return
        }

        viewState = viewState.copy(signingUp = true)
        viewModelScope.launch {
            signUpUseCase.invoke(email, password)
                .onSuccess(::onRegistrationSuccess)
                .onFailure(::onRegistrationFailure)
            viewState = viewState.copy(signingUp = false)
        }
    }

    private fun onRegistrationSuccess(token: String) {
        Timber.d("onRegistrationSuccess: $token")
        signUpFeatureEventHandler.obtainEvent(SignUpFeatureEvent.SignUpSuccess(token))
    }

    private fun onRegistrationFailure(t: Throwable) {
        Timber.e("onRegistrationFailure: $t")
        signUpFeatureEventHandler.obtainEvent(
            SignUpFeatureEvent.SignUpFailure(
                t.message ?: resourceProvider.getString(
                    R.string.text_sign_up_failure_default
                )
            )
        )
    }

    private fun onTextFieldSubmit(event: SignUpScreenEvent.TextFieldSubmit) {
        when (event.textFieldType) {
            SignUpScreenEvent.TextFieldType.EMAIL -> if (!validateEmail(viewState.email)) {
                viewState = viewState.copy(emailError = true)
            }

            SignUpScreenEvent.TextFieldType.PASSWORD -> if (!validatePassword(viewState.password)) {
                viewState = viewState.copy(passwordError = true)
            }

            SignUpScreenEvent.TextFieldType.PASSWORD_CONFIRM -> if (!validateConfirmation(
                    viewState.password,
                    viewState.passwordConfirm
                )
            ) {
                viewState = viewState.copy(passwordConfirmError = true)
            }
        }
    }

    private fun onTextFieldUpdate(event: SignUpScreenEvent.TextFieldUpdate) {
        viewState = when (event.textFieldType) {
            SignUpScreenEvent.TextFieldType.EMAIL -> viewState.copy(
                email = event.value,
                emailError = if (viewState.emailError && validateEmail(event.value)) false else viewState.emailError
            )

            SignUpScreenEvent.TextFieldType.PASSWORD -> viewState.copy(
                password = event.value,
                passwordError = if (viewState.passwordError && validatePassword(event.value)) false else viewState.passwordError
            )

            SignUpScreenEvent.TextFieldType.PASSWORD_CONFIRM -> viewState.copy(
                passwordConfirm = event.value,
                passwordConfirmError = if (viewState.passwordConfirmError && validateConfirmation(
                        viewState.password,
                        viewState.passwordConfirm
                    )
                ) false else viewState.passwordConfirmError
            )
        }
    }

    private fun onVisibilityUpdate() {
        viewState = viewState.copy(passwordVisible = !viewState.passwordVisible)
    }

    private fun onBackClick() {
        signUpFeatureEventHandler.obtainEvent(SignUpFeatureEvent.PopBackStack)
    }

    private fun onSkipClick() {
        signUpFeatureEventHandler.obtainEvent(SignUpFeatureEvent.Skip)
    }

    @AssistedFactory
    interface Factory {
        fun create(signUpFeatureEventHandler: FeatureEventHandler<SignUpFeatureEvent>): SignUpViewModel
    }
}