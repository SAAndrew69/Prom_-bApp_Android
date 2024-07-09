package tech.gelab.cardiograph.profile.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import tech.gelab.cardiograph.profile.impl.BuildConfig
import tech.gelab.cardiograph.profile.impl.R
import tech.gelab.cardiograph.profile.impl.domain.ProfileAction
import tech.gelab.cardiograph.profile.impl.domain.ProfileState
import tech.gelab.cardiograph.ui.theme.spacing

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
    Box(
        modifier
    ) {
        val context = LocalContext.current
        val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        Text(modifier = Modifier.align(Alignment.Center), text = "Экран с профилем")
        Text(
            modifier = Modifier.padding(MaterialTheme.spacing.small).align(Alignment.BottomCenter),
            text = "${stringResource(id = R.string.label_version)} $version",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            fontStyle = FontStyle.Italic
        )
    }
}