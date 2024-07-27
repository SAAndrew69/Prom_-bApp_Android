package tech.gelab.cardiograph.ui.cardiogram

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import kotlin.random.Random

@Composable
fun Cardiogram(
    modifier: Modifier = Modifier,
    label: String? = null,
    strokeColor: Color = Color(CardiogramTokens.STROKE_COLOR),
    cardiogramState: CardiogramState = rememberCardiogramState()
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (label != null) {
            Text(modifier = Modifier.padding(MaterialTheme.spacing.small), text = label)
        }
        Signal(
            modifier = Modifier.fillMaxSize(),
            strokeColor = strokeColor,
            cardiogramState = cardiogramState
        )
    }
}

@Composable
fun Cardiogram(
    modifier: Modifier = Modifier,
    label: String? = null,
    gap: Int,
    maxDisplayValues: Int,
    values: ImmutableList<Float>,
    strokeColor: Color = Color.Black
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.width(20.dp)) {
            if (label != null) {
                Text(modifier = Modifier.fillMaxWidth(), text = label)
            }
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.extraSmall
                )
        ) {
            if (values.size > 1) {
                val xStep = size.width / (maxDisplayValues - 1)
                val yStep = size.height / (gap * 2)

                val yAxis = size.height / 2f

                for (i in 1 until maxDisplayValues) {
                    val listIndex = i - (maxDisplayValues - values.size)
                    if (listIndex < 1 || values.size <= i) continue

                    val prevPoint = values[listIndex - 1]
                    val point = values[listIndex]

                    val offset1 = Offset((i - 1) * xStep, yAxis - prevPoint * yStep)
                    val offset2 = Offset(i * xStep, yAxis - point * yStep)
                    Log.d(
                        "Cardiogram",
                        "drawline: offset1 = $offset1, offset2 = $offset2, size: $size"
                    )
                    drawLine(
                        color = strokeColor,
                        start = offset1,
                        end = offset2,
                        strokeWidth = 2.0f
                    )
                }
            }
        }
    }
}

@Composable
fun Signal(modifier: Modifier = Modifier, strokeColor: Color, cardiogramState: CardiogramState) {
    Canvas(modifier = modifier.background(MaterialTheme.colorScheme.secondaryContainer)) {
        val values = cardiogramState.getValues()
        Log.d(
            "Canvas",
            "draw: list size = ${values.size}, gap = ${cardiogramState.gap}"
        )
        if (values.size > 1) {
            val xStep = size.width / cardiogramState.maxDisplayValues
            val yStep = size.height / (cardiogramState.gap * 2)

            val yAxis = size.height / 2f

            for (i in 1 until cardiogramState.maxDisplayValues) {
                val listIndex = i - (cardiogramState.maxDisplayValues - values.size)
                if (listIndex < 1) continue

                val prevPoint = values[listIndex - 1]
                val point = values[listIndex]

                val offset1 = Offset((i - 1) * xStep, yAxis - prevPoint * yStep)
                val offset2 = Offset(i * xStep, yAxis - point * yStep)
                drawLine(
                    color = strokeColor,
                    start = offset1,
                    end = offset2
                )
            }
        }
    }
}

@Preview(showSystemUi = false)
@Composable
private fun SignalPreview() {
    val scope = LocalLifecycleOwner.current.lifecycleScope
    val cardiogramState = rememberCardiogramState(initialValues = listOf())
    LaunchedEffect(key1 = "Unit") {
        var counter = 0
        scope.launch {
            while (true) {
                var nextValue = Random.nextFloat() * if (counter < 10) 20000 else 50000
                if (nextValue > 10000) {
                    nextValue = -(nextValue - 10000)
                }
                counter++
                cardiogramState.addValue(nextValue)
                delay(500)
            }
        }
    }
    CardiographAppTheme {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Cardiogram(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                strokeColor = Color.Black,
                label = "III",
                cardiogramState = cardiogramState
            )
        }
    }
}