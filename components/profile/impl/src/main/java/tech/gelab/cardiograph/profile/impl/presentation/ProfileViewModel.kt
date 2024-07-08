package tech.gelab.cardiograph.profile.impl.presentation

import tech.gelab.cardiograph.profile.impl.domain.ProfileAction
import tech.gelab.cardiograph.profile.impl.domain.ProfileEvent
import tech.gelab.cardiograph.profile.impl.domain.ProfileState
import tech.gelab.cardiograph.ui.ktx.viewmodel.BaseViewModel

class ProfileViewModel : BaseViewModel<ProfileState, ProfileAction, ProfileEvent>(ProfileState.LoggedOut()) {
    override fun obtainEvent(viewEvent: ProfileEvent) {
        TODO("Not yet implemented")
    }
}