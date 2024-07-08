package tech.gelab.cardiograph.ui.topbar

import androidx.annotation.FloatRange
import androidx.annotation.StringRes

data class TopBarState(
    @StringRes val titleId: Int,
    val showBackButton: Boolean = false,
    @FloatRange(0.0, 1.0) val progress: Float? = null
)
