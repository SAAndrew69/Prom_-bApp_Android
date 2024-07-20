package tech.gelab.cardiograph.measurement.impl.domain

import kotlinx.collections.immutable.ImmutableList
import tech.gelab.cardiograph.ui.topbar.TopBarState

data class MeasurementState(
    val topBarState: TopBarState,
    val supportingText: String,
    val data: ImmutableList<Int>,
    val bottomSheetState: BottomSheetState? = null
)