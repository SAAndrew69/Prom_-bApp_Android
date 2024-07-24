package tech.gelab.cardiograph.conclusion.impl.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.gelab.cardiograph.conclusion.impl.R
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState

@Composable
fun ConclusionScreen(modifier: Modifier = Modifier) {

}

@Composable
fun ConclusionView(modifier: Modifier = Modifier) {
    Column {
        CardioAppBar(topBarState = TopBarState(titleId = R.string.title_conclusion))

        // TODO
        CardioButton(text = stringResource(id = R.string.label_get_conclusion), onClick = { /*TODO*/ })
    }
}