package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme

internal const val progressLabel = "progress_indicator"

@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
    timeLabel: String,
    progress: Float,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    val animatedProgress by animateFloatAsState(targetValue = progress, label = progressLabel)
    Box(modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .align(Alignment.Center),
            color = indicatorColor,
            trackColor = MaterialTheme.colorScheme.outline,
            strokeCap = StrokeCap.Round,
            strokeWidth = 8.dp,
            progress = { animatedProgress },
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = timeLabel,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Preview
@Composable
private fun ProgressIndicatorPrev() {
    CardiographAppTheme {
        ProgressIndicator(timeLabel = "1:30", progress = 0.75f)
    }
}