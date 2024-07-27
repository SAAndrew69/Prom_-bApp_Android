package tech.gelab.cardiograph.ui.cardiogram

import android.util.Log
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
                val values = state.values[m.id]!!
                val gap = state.gaps[m.id]!!

                Log.d("CardiogramList", "values = $values")
                Cardiogram(
                    Modifier.fillMaxWidth().height(70.dp),
                    label = m.label,
                    gap = gap,
                    maxDisplayValues = 50,
                    values = values
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
    }
}