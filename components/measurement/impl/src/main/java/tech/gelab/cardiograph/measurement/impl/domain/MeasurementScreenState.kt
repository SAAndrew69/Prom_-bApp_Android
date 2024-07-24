package tech.gelab.cardiograph.measurement.impl.domain

import tech.gelab.cardiograph.ui.topbar.TopBarState

data class MeasurementScreenState(
    val topBarState: TopBarState,
    val supportingText: String,
    val bottomSheetState: BottomSheetState? = null
)