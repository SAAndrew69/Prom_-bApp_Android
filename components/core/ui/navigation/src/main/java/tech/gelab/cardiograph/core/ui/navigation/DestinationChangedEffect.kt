package tech.gelab.cardiograph.core.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavDestination

@Composable
fun DestinationChangedEffect(
    navController: NavController,
    onDestinationChanged: (NavigationEvent) -> Unit
) {
    DisposableEffect(navController) {
        val destinationChangedListener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                onDestinationChanged(
                    NavigationEvent(
                        controller,
                        destination,
                        arguments
                    )
                )
            }
        navController.addOnDestinationChangedListener(destinationChangedListener)
        onDispose {
            navController.removeOnDestinationChangedListener(destinationChangedListener)
        }
    }
}

data class NavigationEvent(
    val navController: NavController,
    val destination: NavDestination,
    val arguments: Bundle?
)