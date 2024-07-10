package tech.gelab.cardiograph.ui.layout

import androidx.compose.ui.graphics.painter.Painter

class Button(val type: Type, val label: String, val leadingIcon: Painter? = null, val onClick: () -> Unit) {
    enum class Type {
        COMMON,
        TEXT,
        OUTLINE
    }
}