package tech.gelab.cardiograph.ui.cardiogram

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun CardiogramList(
    modifier: Modifier = Modifier,
    multipleCardiogramValueProducer: MultipleCardiogramValueProducer
) {
    val state by multipleCardiogramValueProducer.getStateFlow().collectAsState()
    LazyColumn(modifier) {
        for (m in multipleCardiogramValueProducer.getModels()) {
            item {
                Cardiogram(
                    Modifier.fillMaxWidth().height(100.dp),
                    label = m.label,
                    gap = state.gaps[m.id]!!,
                    maxDisplayValues = 50,
                    values = state.values[m.id]!!
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
    }
}