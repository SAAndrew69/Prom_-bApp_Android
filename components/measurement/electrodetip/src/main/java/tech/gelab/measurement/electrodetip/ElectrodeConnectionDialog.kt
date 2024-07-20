package tech.gelab.measurement.electrodetip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.ui.dialog.CardioAppDialog
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ElectrodeConnectionDialog(viewModel: ElectrodeConnectionViewModel = hiltViewModel()) {
    val state by viewModel.viewStates().collectAsState()
    ElectrodeConnectionView(
        state = state,
        onCheckBoxSelected = viewModel::obtainEvent,
        onCloseClick = viewModel::obtainEvent
    )
}

@Composable
fun ElectrodeConnectionView(
    state: ElectrodeConnectionState,
    onCheckBoxSelected: (ElectrodeConnectionEvent.CheckboxSelected) -> Unit,
    onCloseClick: (ElectrodeConnectionEvent.CloseClicked) -> Unit
) {
    CardioAppDialog(
        title = {
            Text(
                text = stringResource(R.string.title_electrode_tip),
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            BodyView(
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                state = state,
                onCheckBoxSelected = onCheckBoxSelected
            )
        },
        buttons = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { onCloseClick(ElectrodeConnectionEvent.CloseClicked) }) {
                    Text(text = stringResource(id = R.string.label_close))
                }
            }
        }
    )
}

@Composable
fun BodyView(
    modifier: Modifier = Modifier,
    state: ElectrodeConnectionState,
    onCheckBoxSelected: (ElectrodeConnectionEvent.CheckboxSelected) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_connection_tip),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .padding(
                    start = MaterialTheme.spacing.large,
                    top = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.large,
                    bottom = MaterialTheme.spacing.extraLarge
                ),
            painter = painterResource(id = R.drawable.image_electrode_connection),
            contentDescription = null
        )
        if (state.showCheckbox) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = state.checkboxSelected,
                    onCheckedChange = {
                        onCheckBoxSelected(ElectrodeConnectionEvent.CheckboxSelected(it))
                    }
                )
                Text(
                    text = stringResource(R.string.label_not_show_again),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun ElectrodeConnectionViewPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
        ) {
            ElectrodeConnectionView(
                state = ElectrodeConnectionState(showCheckbox = true, checkboxSelected = true),
                {}, {})
        }
    }
}