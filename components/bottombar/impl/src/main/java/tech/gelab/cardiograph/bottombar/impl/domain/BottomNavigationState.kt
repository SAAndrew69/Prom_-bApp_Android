package tech.gelab.cardiograph.bottombar.impl.domain

import tech.gelab.cardiograph.bottombar.impl.R
import tech.gelab.cardiograph.ui.topbar.TopBarState

data class BottomNavigationState(
    val topBarState: TopBarState = TopBarState(R.string.label_measure_preparation, showBackButton = false),
    val selectedItemIndex: Int = 0,
    val bottomBarHidden: Boolean = false
)