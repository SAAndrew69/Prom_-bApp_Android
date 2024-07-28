package tech.gelab.cardiograph.idpicker.newemployee.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme

@Composable
fun OutlinedText(modifier: Modifier = Modifier, text: String) {
    Row(
        modifier = modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.small
        )
    ) {
        Text(
            modifier = Modifier.padding(7.dp),
            text = text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun OutlinedTextPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            OutlinedText(text = "ID 1234")
        }
    }
}