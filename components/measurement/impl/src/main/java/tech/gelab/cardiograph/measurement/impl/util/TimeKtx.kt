package tech.gelab.cardiograph.measurement.impl.util

import android.annotation.SuppressLint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun timeFlow(): Flow<Int> {
    return flow {
        var counter = 0

        while (counter != 120) {
            counter += 1
            delay(1000)
        }
    }
}

@SuppressLint("DefaultLocale")
fun getTimeLabel(seconds: Int): String {
    return String.format("%02d:%02d", seconds / 60, seconds % 60)
}