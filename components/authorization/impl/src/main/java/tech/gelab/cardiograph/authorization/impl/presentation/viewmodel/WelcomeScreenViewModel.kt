package tech.gelab.cardiograph.authorization.impl.presentation.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.gelab.cardiograph.authorization.impl.AuthFeatureEvent
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeAction
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenEvent
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenState
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.network.NetworkManager
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel(assistedFactory = WelcomeScreenViewModel.Factory::class)
class WelcomeScreenViewModel @AssistedInject constructor(
    @Assisted private val authFeatureEventHandler: FeatureEventHandler<AuthFeatureEvent>,
    private val networkManager: NetworkManager
) : BaseViewModel<WelcomeScreenState, WelcomeAction, WelcomeScreenEvent>(
    getInitialState(
        networkManager
    )
), LifecycleEventObserver {

    companion object {
        fun getInitialState(networkManager: NetworkManager): WelcomeScreenState {
            return if (networkManager.isNetworkEnabled()) WelcomeScreenState.NetworkConnectable
            else WelcomeScreenState.NoNetwork
        }
    }

    override fun obtainEvent(viewEvent: WelcomeScreenEvent) {
        when (viewEvent) {
            WelcomeScreenEvent.LOGIN -> authFeatureEventHandler.obtainEvent(AuthFeatureEvent.NavigateLogin)
            WelcomeScreenEvent.REGISTER -> authFeatureEventHandler.obtainEvent(AuthFeatureEvent.NavigateSignUp)
            WelcomeScreenEvent.SKIP -> authFeatureEventHandler.obtainEvent(AuthFeatureEvent.NavigateSkipAuth)
            WelcomeScreenEvent.NETWORK_SETTINGS_CLICK -> {  }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        // TODO debug
        Timber.d("onStateChanged: $source, event: $event")
        clearAction()
    }

    @AssistedFactory
    interface Factory {
        fun create(authFeatureEventHandler: FeatureEventHandler<AuthFeatureEvent>): WelcomeScreenViewModel
    }
}