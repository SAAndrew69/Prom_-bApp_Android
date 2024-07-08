package tech.gelab.cardiograph.bottombar.impl.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import tech.gelab.cardiograph.bottombar.impl.R
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute

enum class NavigationItem(
    val route: NavigationRoute,
    @DrawableRes val iconRes: Int,
    @StringRes val labelRes: Int
) {
    MEASUREMENT(NavigationRoute.IDENTIFIER_PICKER, R.drawable.icon_measure, R.string.label_measure),
    REPORTS(NavigationRoute.REPORTS, R.drawable.icon_reports, R.string.label_reports),
    PROFILE(NavigationRoute.PROFILE, R.drawable.icon_profile, R.string.title_profile)
}