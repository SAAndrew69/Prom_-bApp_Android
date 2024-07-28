package tech.gelab.cardiograph.idpicker.newemployee.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.idpicker.newemployee.R
import tech.gelab.cardiograph.room.Gender
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.ktx.element.CardioOutlinedTextField
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.idpicker.newemployee.domain.NewEmployeeEvent
import tech.gelab.cardiograph.idpicker.newemployee.domain.ViewState

@Composable
fun NewEmployeeScreen(viewModel: NewEmployeeViewModel = hiltViewModel()) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(initial = null)
    NewEmployeeView(
        modifier = Modifier.fillMaxSize(),
        state = viewState,
        onEvent = viewModel::obtainEvent
    )
}

@Composable
fun NewEmployeeView(
    modifier: Modifier = Modifier,
    state: ViewState,
    onEvent: (NewEmployeeEvent) -> Unit
) {
    Box(modifier) {
        Inputs(state = state, onEvent = onEvent)
        CardioButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            text = stringResource(id = R.string.label_proceed),
            enabled = state.proceedEnabled,
            onClick = { onEvent(NewEmployeeEvent.ProceedClick) }
        )
    }
}

@Composable
fun Inputs(modifier: Modifier = Modifier, state: ViewState, onEvent: (NewEmployeeEvent) -> Unit) {
    Column {
        Row(modifier.padding(top = 40.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedText(text = state.idLabel)
            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.small),
                text = stringResource(id = R.string.label_new_employee),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Column(modifier = Modifier.padding(top = 40.dp)) {
            Text(
                text = stringResource(id = R.string.label_pick_gender),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            GenderButton(
                modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall),
                gender = Gender.MALE,
                state = state,
                onEvent = onEvent
            )
            GenderButton(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                gender = Gender.FEMALE,
                state = state,
                onEvent = onEvent
            )
        }
        Column(modifier = Modifier.padding(top = 40.dp)) {
            Text(
                text = stringResource(id = R.string.label_date_label),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            CardioOutlinedTextField(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                value = state.dateOfBirthString ?: "",
                onValueChange = { },
                placeholder = stringResource(id = R.string.text_date_hint),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_calendar),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Composable
fun GenderButton(
    modifier: Modifier = Modifier,
    gender: Gender,
    state: ViewState,
    onEvent: (NewEmployeeEvent.GenderPick) -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = state.pickedGender == gender,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.secondary),
            onClick = { onEvent(NewEmployeeEvent.GenderPick(gender)) }
        )
        Text(
            text = stringResource(
                id = when (gender) {
                    Gender.MALE -> R.string.label_male
                    Gender.FEMALE -> R.string.label_female
                }
            ),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun NewEmployeePrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            NewEmployeeView(
                modifier = Modifier.fillMaxSize(),
                state = ViewState(
                    idLabel = "ID 1234",
                    pickedGender = Gender.MALE,
                    dateOfBirthString = ""
                )
            ) {}
        }
    }
}