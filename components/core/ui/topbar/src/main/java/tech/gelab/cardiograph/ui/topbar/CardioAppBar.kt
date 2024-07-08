package tech.gelab.cardiograph.ui.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

// TODO resize
private object CardioAppBarTokens {
    val ContainerHeight = 64.dp
}

@Composable
fun CardioAppBar(
    modifier: Modifier = Modifier,
    topBarState: TopBarState,
    onBackButtonClick: () -> Unit = {},
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(CardioAppBarTokens.ContainerHeight)
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        if (topBarState.showBackButton) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBackButtonClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_chevron_left),
                    contentDescription = null
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineMedium,
            text = stringResource(topBarState.titleId)
        )
    }
}

@Preview
@Composable
private fun AppBarPrev() {
    CardiographAppTheme {
        CardioAppBar(
            topBarState = TopBarState(titleId = R.string.test_label_login, true, null),
            onBackButtonClick = {})
    }
}