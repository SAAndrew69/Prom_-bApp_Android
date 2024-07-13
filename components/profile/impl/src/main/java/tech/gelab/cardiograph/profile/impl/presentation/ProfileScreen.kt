package tech.gelab.cardiograph.profile.impl.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.profile.impl.R
import tech.gelab.cardiograph.profile.impl.domain.ProfileAction
import tech.gelab.cardiograph.profile.impl.domain.ProfileEvent
import tech.gelab.cardiograph.profile.impl.domain.ProfileState
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(initial = null)

    ProfileView(
        modifier = Modifier.fillMaxSize(),
        viewState = viewState,
        viewAction = viewAction,
        onEvent = viewModel::obtainEvent
    )
}

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    viewState: ProfileState,
    viewAction: ProfileAction?,
    onEvent: (ProfileEvent) -> Unit
) {
    Box(modifier) {
        Text(modifier = Modifier.align(Alignment.Center), text = "Экран с профилем")
        Text(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .align(Alignment.BottomCenter),
            text = "${stringResource(id = R.string.label_version)} ${viewState.appVersion}",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            fontStyle = FontStyle.Italic
        )
        if (viewState.authorized) {
            AuthorizedView()
        } else {
            LoggedOutView(
                modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.small),
                viewState = viewState,
                viewAction = viewAction,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun LoggedOutView(
    modifier: Modifier = Modifier,
    viewState: ProfileState,
    viewAction: ProfileAction?,
    onEvent: (ProfileEvent) -> Unit
) {
    Box(modifier) {
        Column(Modifier.align(Alignment.BottomCenter)) {
            CardioButton(
                modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.spacing.small),
                text = stringResource(R.string.label_login),
                onClick = { onEvent(ProfileEvent.Login) })
            AnimatedVisibility(visible = viewState.disconnectVisible) {
                CardioButton(
                    modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.spacing.small),
                    text = stringResource(R.string.label_disconnect_device),
                    onClick = { onEvent(ProfileEvent.Disconnect) }
                )
            }
        }
    }
}

@Composable
fun AuthorizedView(modifier: Modifier = Modifier) {

}