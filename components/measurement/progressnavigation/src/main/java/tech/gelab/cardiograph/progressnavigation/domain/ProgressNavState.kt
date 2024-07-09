package tech.gelab.cardiograph.progressnavigation.domain

import tech.gelab.cardiograph.measurement.progressnavigation.R
import tech.gelab.cardiograph.ui.topbar.TopBarState

data class ProgressNavState(
    val topBarState: TopBarState = TopBarState(
        R.string.title_prepare_measure,
        showBackButton = true,
        progress = 0f
    )
)