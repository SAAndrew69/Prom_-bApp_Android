package tech.gelab.cardiograph.authorization.impl.presentation.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import tech.gelab.cardiograph.authorization.impl.AuthFeatureEvent
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeAction
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenEvent
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenState
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.network.NetworkManager
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import timber.log.Timber

@HiltViewModel(assistedFactory = WelcomeScreenViewModel.Factory::class)
class WelcomeScreenViewModel @AssistedInject constructor(
    @Assisted private val authFeatureEventHandler: FeatureEventHandler<AuthFeatureEvent>,
    private val networkManager: NetworkManager
) : BaseViewModel<WelcomeScreenState, WelcomeAction, WelcomeScreenEvent>(
    getInitialState(networkManager)
), LifecycleEventObserver {

    companion object {
        fun getInitialState(networkManager: NetworkManager): WelcomeScreenState {
            return if (networkManager.isNetworkEnabled()) WelcomeScreenState.NetworkConnectable
            else WelcomeScreenState.NoNetwork
        }
    }

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
                AuthFeatureEvent.SkipNetworkConnection
            )
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // TODO debug
        Timber.d("onStateChanged: $source, event: $event")
        clearAction()
    }

    private fun onNetworkState(isEnabled: Boolean) {
        viewState = if (isEnabled) {
            WelcomeScreenState.NetworkConnectable
        } else {
            WelcomeScreenState.NoNetwork
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(authFeatureEventHandler: FeatureEventHandler<AuthFeatureEvent>): WelcomeScreenViewModel
    }
}