package tech.gelab.cardiograph.core.ui.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavHostProvider = staticCompositionLocalOf<NavHostController> {
    error("No NavHostController provided")
}