package tech.gelab.cardiograph.authorization.impl.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.authorization.impl.R
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenEvent
import tech.gelab.cardiograph.authorization.impl.domain.WelcomeScreenState
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.ktx.element.CardioTextButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing


@Composable
fun WelcomeScreen(viewModel: WelcomeScreenViewModel = hiltViewModel()) {
    val viewState by viewModel.viewStates().collectAsState()

    when (viewState) {
        is WelcomeScreenState.NetworkConnectable -> WelcomeView(onEvent = viewModel::obtainEvent)
        WelcomeScreenState.NoNetwork -> ConnectivityInternetView(onEvent = viewModel::obtainEvent)
    }
}

@Composable
fun WelcomeView(modifier: Modifier = Modifier, onEvent: (WelcomeScreenEvent) -> Unit) {
    Layout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.small),
        content = {
            WelcomeImage(Modifier.padding(MaterialTheme.spacing.medium))
            WelcomeButtons(onEvent = onEvent)
        }
    ) { measurables, constraints ->
        check(measurables.size == 2)
        val placeables = measurables.map {
            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }

        val welcomeImagePlaceable = placeables[0]
        val buttonGroup = placeables[1]

        val minusButtonGroupMiddle = (constraints.maxHeight - buttonGroup.height) / 2

        layout(constraints.maxWidth, constraints.minHeight) {
            welcomeImagePlaceable.place(
                0,
                minusButtonGroupMiddle - welcomeImagePlaceable.height / 2
            )
            buttonGroup.place(0, constraints.maxHeight - buttonGroup.height)
        }
    }
}

@Composable
fun WelcomeImage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.title_welcome),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        Image(painter = painterResource(R.drawable.image_welcome), contentDescription = null)
    }
}

@Composable
fun WelcomeButtons(modifier: Modifier = Modifier, onEvent: (WelcomeScreenEvent) -> Unit) {
    Column(modifier) {
        CardioButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.label_login),
            onClick = { onEvent(WelcomeScreenEvent.LOGIN) })
        CardioButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_register),
            onClick = { onEvent(WelcomeScreenEvent.REGISTER) })
        CardioTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_skip_auth),
            onClick = { onEvent(WelcomeScreenEvent.SKIP_AUTH) }
        )
    }
}

@Preview
@Composable
private fun WelcomeViewPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            WelcomeView(onEvent = {})
        }
    }
}