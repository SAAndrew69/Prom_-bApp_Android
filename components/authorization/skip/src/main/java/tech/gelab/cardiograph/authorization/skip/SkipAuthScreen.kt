package tech.gelab.cardiograph.authorization.skip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.ui.ktx.element.CardioAppButton
import tech.gelab.cardiograph.ui.ktx.element.CardioAppTextButton
import tech.gelab.cardiograph.ui.ktx.element.RationaleImageView
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState


@Composable
fun SkipAuthScreen(onSkipAuthEvent: (SkipAuthEvent) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        CardioAppBar(
            topBarState = TopBarState(
                titleId = R.string.title_skip_auth,
                showBackButton = true
            ), onBackButtonClick = { onSkipAuthEvent(SkipAuthEvent.GET_BACK) }
        )
        SkipAuthView(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.spacing.small),
            onEvent = onSkipAuthEvent
        )
    }
}

@Composable
fun SkipAuthView(modifier: Modifier = Modifier, onEvent: (SkipAuthEvent) -> Unit) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        RationaleImageView(
            modifier = Modifier.fillMaxWidth(),
            imageRes = R.drawable.image_auth_skip,
            questionText = R.string.text_skip_auth_question,
            text = R.string.text_skip_auth1,
            featureText1 = R.string.text_skip_auth2,
            featureText2 = R.string.text_skip_auth3
        )
        SkipAuthButtons(onEvent = onEvent)
    }
}

@Composable
fun SkipAuthButtons(modifier: Modifier = Modifier, onEvent: (SkipAuthEvent) -> Unit) {
    Column(modifier) {
        CardioAppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_skip_auth_get_back),
            onClick = { onEvent(SkipAuthEvent.GET_BACK) }
        )
        CardioAppTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_skip_authorization),
            onClick = { onEvent(SkipAuthEvent.CONTINUE) })
    }
}

@Preview
@Composable
private fun SkipAuthPrev() {
    CardiographAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            SkipAuthView(
                Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.small)
            ) {

            }
        }
    }
}