package tech.gelab.cardiograph.ui.cardiogram

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.math.abs

object CardiogramTokens {
    const val STROKE_COLOR = 0x000000
    const val HEIGHT = 100
}

@Stable
class CardiogramState internal constructor(initialValues: List<Float>) {

    var gap by mutableIntStateOf(CardiogramDefaults.DEFAULT_GAP)
    var maxDisplayValues by mutableIntStateOf(CardiogramDefaults.DEFAULT_DISPLAY_VALUES)

    private val values: MutableList<Float> = mutableStateListOf(*initialValues.toTypedArray())

    fun addValue(value: Float) {
        Log.d("CardiogramState", "value = $value")
        val absValue = abs(value)

        if (absValue > gap) {
            gap = (absValue.toInt() / CardiogramDefaults.DEFAULT_GAP + 1) * CardiogramDefaults.DEFAULT_GAP
        }
        values.add(value)
        if (values.size > maxDisplayValues) {
            while (values.size > maxDisplayValues) {
                values.removeAt(0)
            }
        }
    }

    fun getValues(): List<Float> {
        return values.toList()
    }

    companion object {
        // TODO
        val Saver: Saver<CardiogramState, *> = Saver(
            save = { it.getValues() },
            restore = { CardiogramState(listOf()) }
        )
    }

}

@Composable
fun rememberCardiogramState(
    initialValues: List<Float> = listOf()
): CardiogramState {
    return rememberSaveable(saver = CardiogramState.Saver) {
        CardiogramState(initialValues)
    }
}