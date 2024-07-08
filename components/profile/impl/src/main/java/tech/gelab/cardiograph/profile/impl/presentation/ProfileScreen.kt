package tech.gelab.cardiograph.profile.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.profile.impl.domain.ProfileAction
import tech.gelab.cardiograph.profile.impl.domain.ProfileState

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {
    val viewState by profileViewModel.viewStates().collectAsState()
    val viewAction by profileViewModel.viewActions().collectAsState(initial = null)

    ProfileView(modifier = Modifier.fillMaxSize(), viewState = viewState, viewAction = viewAction)
}

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    viewState: ProfileState,
    viewAction: ProfileAction?
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Экран с профилем")
    }
}