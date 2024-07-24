package tech.gelab.cardiograph.ui.cardiogram

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap

class CardiogramListState(
    val gaps: ImmutableMap<String, Int>,
    val values: Map<String, ImmutableList<Float>>
)