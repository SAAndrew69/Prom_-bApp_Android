package tech.gelab.cardiograph.idpicker.impl.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.idpicker.impl.R
import tech.gelab.cardiograph.idpicker.impl.domain.PickerAction
import tech.gelab.cardiograph.idpicker.impl.domain.PickerEvent
import tech.gelab.cardiograph.idpicker.impl.domain.PickerState
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.ktx.element.CardioOutlinedTextField
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun IdentifierPickerScreen(viewModel: IdentifierPickerViewModel = hiltViewModel()) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(initial = null)

    PickerView(Modifier.fillMaxSize(), viewState, viewAction, viewModel::obtainEvent)
}

@Composable
fun PickerView(
    modifier: Modifier = Modifier,
    viewState: PickerState,
    viewAction: PickerAction?,
    onEvent: (PickerEvent) -> Unit
) {
//    if (viewState.isDeviceConnected) {
        Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.padding(MaterialTheme.spacing.small)) {
                Text(
                    text = stringResource(R.string.text_picker_tip),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                RadioView(viewState = viewState, viewAction = viewAction, onEvent = onEvent)
            }
            CardioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.small,
                        vertical = MaterialTheme.spacing.medium
                    ),
                text = stringResource(R.string.label_next),
                enabled = viewState.pickedGroupIndex != null,
                onClick = { onEvent(PickerEvent.NextClick) }
            )
        }
        // TODO
//    } else {
//        Box(modifier) {
//            Column(
//                Modifier
//                    .fillMaxWidth()
//                    .padding(MaterialTheme.spacing.small)
//                    .align(Alignment.Center)
//            ) {
//                Text(text = stringResource(R.string.text_disconnected1))
//                Text(text = stringResource(R.string.text_disconnected2))
//            }
//            CardioButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(
//                        horizontal = MaterialTheme.spacing.small,
//                        vertical = MaterialTheme.spacing.medium
//                    )
//                    .align(Alignment.BottomCenter),
//                text = stringResource(R.string.label_connect_device),
//                onClick = { onEvent(PickerEvent.ConnectDeviceClick) })
//        }
//    }
}

@Composable
fun RadioView(
    modifier: Modifier = Modifier,
    viewState: PickerState,
    viewAction: PickerAction?,
    onEvent: (PickerEvent) -> Unit
) {
    Column(modifier) {
        NewEmployeeGroup(
            selected = viewState.pickedGroupIndex == 0,
            newEmployeeId = viewState.newEmployeeId,
            onEvent = { onEvent(PickerEvent.GroupRadioClick(0)) })
        ListGroup(
            modifier = Modifier.padding(top = MaterialTheme.spacing.small),
            selected = viewState.pickedGroupIndex == 1,
            pickedElementIndex = viewState.pickedListEmployee,
            viewAction = viewAction,
            viewState = viewState,
            onEvent = onEvent
        )
        ManualGroup(
            modifier = Modifier.padding(top = MaterialTheme.spacing.small),
            selected = viewState.pickedGroupIndex == 2,
            viewState = viewState,
            onEvent = onEvent
        )
    }
}

@Composable
fun NewEmployeeGroup(
    modifier: Modifier = Modifier,
    selected: Boolean,
    newEmployeeId: Int,
    onEvent: (PickerEvent) -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            colors = RadioButtonDefaults.colors()
                .copy(selectedColor = MaterialTheme.colorScheme.secondary),
            selected = selected,
            onClick = { onEvent(PickerEvent.GroupRadioClick(0)) })
        Text(
            text = stringResource(id = R.string.label_new_employee),
            style = MaterialTheme.typography.labelMedium
        )
        Text(text = "ID $newEmployeeId", style = MaterialTheme.typography.labelMedium)
    }
    Text(
        text = stringResource(id = R.string.text_new_employee),
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
fun ListGroup(
    modifier: Modifier = Modifier,
    selected: Boolean,
    pickedElementIndex: Int?,
    viewState: PickerState,
    viewAction: PickerAction?,
    onEvent: (PickerEvent) -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.Top) {
        RadioButton(
            colors = RadioButtonDefaults.colors()
                .copy(selectedColor = MaterialTheme.colorScheme.secondary),
            selected = selected,
            onClick = { onEvent(PickerEvent.GroupRadioClick(1)) })
        Column {
            Text(
                text = stringResource(R.string.label_pick_from_list),
                style = MaterialTheme.typography.labelMedium
            )
            CardioOutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                value = "",
                onValueChange = {},
                placeholder = stringResource(R.string.text_placeholder_pick_id)
            )
        }
    }
}

@Composable
fun ManualGroup(
    modifier: Modifier = Modifier,
    selected: Boolean,
    viewState: PickerState,
    onEvent: (PickerEvent) -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.Top) {
        RadioButton(
            colors = RadioButtonDefaults.colors()
                .copy(selectedColor = MaterialTheme.colorScheme.secondary),
            selected = selected,
            onClick = { onEvent(PickerEvent.GroupRadioClick(2)) })
        Column {
            Text(
                text = stringResource(R.string.label_manual_input),
                style = MaterialTheme.typography.labelMedium
            )
            CardioOutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                value = viewState.userInput,
                onValueChange = { onEvent(PickerEvent.ManualInputChange(it)) },
                placeholder = stringResource(R.string.text_placeholder_manual_input)
            )
        }
    }
}

@Preview
@Composable
private fun PickerViewPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            PickerView(Modifier.fillMaxSize(), PickerState(), null, {})
        }
    }
}