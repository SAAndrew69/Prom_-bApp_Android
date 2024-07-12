package tech.gelab.cardiograph.authorization.impl.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.gelab.cardiograph.authorization.impl.AuthFeatureEvent
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeAction
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenEvent
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenState
import tech.gelab.cardiograph.authorization.impl.domain.usecase.GetInitialStateUseCase
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.network.NetworkManager
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    getInitialStateUseCase: GetInitialStateUseCase,
    private val authFeatureEventHandler: FeatureEventHandler<AuthFeatureEvent>,
    private val networkManager: NetworkManager
) : BaseViewModel<WelcomeScreenState, WelcomeAction, WelcomeScreenEvent>(getInitialStateUseCase.invoke()) {

    init {
        networkManager.getNetworkStateFlow()
            .onEach(::onNetworkState)
            .launchIn(viewModelScope)
    }

    override fun obtainEvent(viewEvent: WelcomeScreenEvent) {
        when (viewEvent) {
            WelcomeScreenEvent.LOGIN -> authFeatureEventHandler.obtainEvent(AuthFeatureEvent.NavigateLogin)
            WelcomeScreenEvent.REGISTER -> authFeatureEventHandler.obtainEvent(AuthFeatureEvent.NavigateSignUp)
            WelcomeScreenEvent.SKIP_AUTH -> authFeatureEventHandler.obtainEvent(AuthFeatureEvent.NavigateSkipAuth)
            WelcomeScreenEvent.NETWORK_SETTINGS_CLICK -> authFeatureEventHandler.obtainEvent(
                AuthFeatureEvent.OpenNetworkSettings
            )

            WelcomeScreenEvent.SKIP_NETWORK_CONNECTION -> authFeatureEventHandler.obtainEvent(
                AuthFeatureEvent.NavigateScanner
            )
        }
    }

    private fun onNetworkState(isEnabled: Boolean) {
        viewState = if (isEnabled) {
            WelcomeScreenState.NetworkConnectable
        } else {
            WelcomeScreenState.NoNetwork
        }
    }
}