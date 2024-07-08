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
            SignUpScreenEvent.BackClick -> TODO()
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
                resourceProvider.getString(
                    R.string.text_sign_up_failure_default
                )
            )
        )

    }

    private fun onTextFieldSubmit(event: SignUpScreenEvent.TextFieldSubmit) {

    }

    private fun onTextFieldUpdate(event: SignUpScreenEvent.TextFieldUpdate) {
        viewState = when (event.textFieldType) {
            SignUpScreenEvent.TextFieldType.EMAIL -> viewState.copy(email = event.value)
            SignUpScreenEvent.TextFieldType.PASSWORD -> viewState.copy(password = event.value)
            SignUpScreenEvent.TextFieldType.PASSWORD_CONFIRM -> viewState.copy(passwordConfirm = event.value)
        }
    }

    private fun onVisibilityUpdate() {
        viewState = viewState.copy(passwordVisible = !viewState.passwordVisible)
    }

    private fun onSkipClick() {
        signUpFeatureEventHandler.obtainEvent(SignUpFeatureEvent.Skip)
    }

    @AssistedFactory
    interface Factory {
        fun create(signUpFeatureEventHandler: FeatureEventHandler<SignUpFeatureEvent>): SignUpViewModel
    }
}