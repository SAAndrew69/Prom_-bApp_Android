package tech.gelab.cardiograph.idpicker.newemployee.presentation

import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(viewModel: DatePickerViewModel = hiltViewModel()) {
    val viewState by viewModel.viewStates().collectAsState()

    val datePickerState = rememberDatePickerState()
    DatePicker(state = datePickerState)

}