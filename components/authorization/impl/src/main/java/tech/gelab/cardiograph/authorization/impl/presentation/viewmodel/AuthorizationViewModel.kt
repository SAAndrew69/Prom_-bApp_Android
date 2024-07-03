package tech.gelab.cardiograph.authorization.impl.presentation.viewmodel

import android.content.res.loader.ResourcesProvider
import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.authorization.impl.R
import tech.gelab.cardiograph.authorization.impl.domain.AuthorizationEventHandler
import tech.gelab.cardiograph.authorization.impl.domain.usecase.AuthorizationUseCase
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.util.ResourceProvider
import javax.inject.Inject

@HiltViewModel(assistedFactory = AuthorizationViewModel.Factory::class)
class AuthorizationViewModel @AssistedInject constructor(
    private val authService: AuthService,
    private val toastHelper: ToastHelper,
    private val resourceProvider: ResourceProvider,
    @Assisted private val authorizationEventHandler: AuthorizationEventHandler
) : ViewModel() {

    var email by mutableStateOf("")
    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else false
    }

    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)



    private fun onAuthFailure(t: Throwable) {
        val msg = buildString {
            append(resourceProvider.getString(R.string.text_auth_error))
            append(": $t")
        }
        toastHelper.showToast(msg, Toast.LENGTH_SHORT)
    }

    // TODO: change result value
    private fun onAuthSuccess(b: Boolean) {

    }

    fun updateEmail(input: String) {
        email = input
    }

    fun updatePassword(input: String) {
        password = input
    }

    fun updatePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun onPasswordSubmit() {

    }

    fun onAuthorizeClick() {
        viewModelScope.launch {
            AuthorizationUseCase(authService).invoke()
                .onFailure(::onAuthFailure)
                .onSuccess(::onAuthSuccess)
        }
    }

    fun onAuthSkipClick() {
        authorizationEventHandler.onAuthorizationSkip()
    }

    @AssistedFactory
    interface Factory {
        fun create(authorizationEventHandler: AuthorizationEventHandler): AuthorizationViewModel
    }
}