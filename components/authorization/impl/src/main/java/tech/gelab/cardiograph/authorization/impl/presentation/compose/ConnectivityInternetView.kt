package tech.gelab.cardiograph.authorization.impl.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.authorization.impl.R
import tech.gelab.cardiograph.authorization.impl.domain.AuthButtonModel
import tech.gelab.cardiograph.authorization.impl.domain.ButtonType
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenEvent
import tech.gelab.cardiograph.authorization.util.FeatureEnableContent
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ConnectivityInternetView(onEvent: (WelcomeScreenEvent) -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.title_connectivity_internet),
                style = MaterialTheme.typography.headlineSmall
            )
            Image(
                painter = painterResource(id = R.drawable.image_connectivity_internet),
                contentDescription = null
            )
        }

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

@Preview
@Composable
private fun NoNetworkViewPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ConnectivityInternetView(onEvent = {})
        }
    }
}