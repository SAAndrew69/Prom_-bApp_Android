package tech.gelab.cardiograph.profile.impl.presentation

import android.widget.Toast
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.core.notification.ToastHelper
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.profile.impl.ProfileFeatureEvent
import tech.gelab.cardiograph.profile.impl.R
import tech.gelab.cardiograph.profile.impl.domain.ProfileAction
import tech.gelab.cardiograph.profile.impl.domain.ProfileEvent
import tech.gelab.cardiograph.profile.impl.domain.ProfileState
import tech.gelab.cardiograph.profile.impl.domain.usecase.DisconnectClickUseCase
import tech.gelab.cardiograph.profile.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getInitialStateUseCase: GetInitialStateUseCase,
    private val profileFeatureEventHandler: FeatureEventHandler<ProfileFeatureEvent>,
    private val disconnectClickUseCase: DisconnectClickUseCase,
    private val toastHelper: ToastHelper
) : BaseViewModel<ProfileState, ProfileAction, ProfileEvent>(getInitialStateUseCase.invoke()) {

    override fun obtainEvent(viewEvent: ProfileEvent) {
        when (viewEvent) {
            ProfileEvent.ChangeUser -> onChangeUser()
            ProfileEvent.DeleteAccount -> onDeleteAccount()
            ProfileEvent.Disconnect -> onDisconnectClick()
            ProfileEvent.Login -> onLoginClick()
        }
    }

    private fun onChangeUser() {

    }

    private fun onDeleteAccount() {

    }

    private fun onDisconnectClick() {
        viewState = viewState.copy(disconnectVisible = false)
        viewModelScope.launch {
            val res = disconnectClickUseCase.invoke()
            if (!res) {
                toastHelper.showToast(R.string.text_disconnect_failure, Toast.LENGTH_SHORT)
                viewState = viewState.copy(disconnectVisible = true)
            }
        }
    }

    private fun onLoginClick() {
        profileFeatureEventHandler.obtainEvent(ProfileFeatureEvent.NavigateAuthorization)
    }
}