package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun OutlinedText(modifier: Modifier = Modifier, label: String, text: String) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall),
            text = label,
            style = MaterialTheme.typography.labelMedium
        )
        Box(
            Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = MaterialTheme.shapes.small
                )
                .padding(MaterialTheme.spacing.extraSmall)
                .width(100.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}