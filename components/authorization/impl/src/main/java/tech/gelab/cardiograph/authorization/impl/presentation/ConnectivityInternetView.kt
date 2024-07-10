package tech.gelab.cardiograph.authorization.impl.presentation

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.authorization.impl.R
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenEvent
import tech.gelab.cardiograph.ui.ktx.element.FeatureEnableContent
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.ktx.element.CardioTextButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ConnectivityInternetView(modifier: Modifier = Modifier, onEvent: (WelcomeScreenEvent) -> Unit) {
    Column(
        modifier.padding(MaterialTheme.spacing.small),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ConnectivityInfo(modifier = Modifier.padding(top = MaterialTheme.spacing.extraLarge))
        ConnectivityButtons(modifier = Modifier.fillMaxWidth(), onEvent = onEvent)
    }
}

@Composable
fun ConnectivityInfo(modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.title_connectivity_internet),
            style = MaterialTheme.typography.headlineSmall
        )
        Image(
            painter = painterResource(id = R.drawable.image_connectivity_internet),
            contentDescription = null
        )

        FeatureEnableContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            text = stringResource(id = R.string.text_connectivity_internet_1),
            featureText1 = stringResource(id = R.string.text_connectivity_internet_2),
            featureText2 = stringResource(id = R.string.text_connectivity_internet_3)
        )
    }
}

@Composable
fun ConnectivityButtons(modifier: Modifier = Modifier, onEvent: (WelcomeScreenEvent) -> Unit) {
    val settingsActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { /*TODO add handler*/ }
    )
    Column(modifier) {
        CardioButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_internet_settings),
            onClick = {
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                settingsActivityResult.launch(intent)
            }
        )
        CardioTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.small, bottom = MaterialTheme.spacing.small),
            text = stringResource(id = R.string.label_continue_without_network),
            onClick = { onEvent(WelcomeScreenEvent.SKIP_NETWORK_CONNECTION) })
    }
}

@Preview
@Composable
private fun NoNetworkViewPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ConnectivityInternetView(modifier = Modifier.fillMaxSize(), onEvent = {})
        }
    }
}