package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ExerciseBottomSheet(
    modifier: Modifier = Modifier,
    onExerciseComplete: () -> Unit,
    onStartAgainClick: () -> Unit
) {
    Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                text = stringResource(id = R.string.label_uploaded),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                text = stringResource(id = R.string.text_exercise),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.small),
                text = stringResource(id = R.string.text_click_on_exercise_complete),
                style = MaterialTheme.typography.labelLarge
            )
        }
        Column {
            CardioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small),
                text = stringResource(id = R.string.label_exercise_complete),
                onClick = onExerciseComplete
            )
            RestartButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.small),
                onClick = onStartAgainClick
            )
        }
    }
}

@Preview
@Composable
private fun ExerciseBottomSheetPrev() {
    CardiographAppTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.surface)) {
            ExerciseBottomSheet(Modifier.fillMaxWidth(), onExerciseComplete = {}, onStartAgainClick = {})
        }
    }
}