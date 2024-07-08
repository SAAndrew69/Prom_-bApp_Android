package tech.gelab.cardiograph.ui.ktx.element

import androidx.compose.runtime.Composable

fun <T> T?.letCompose(block: @Composable (T) -> Unit): (@Composable () -> Unit)? {
    if (this == null) return null
    return { block(this) }
}