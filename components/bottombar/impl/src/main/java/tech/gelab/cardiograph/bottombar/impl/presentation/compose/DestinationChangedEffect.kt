package tech.gelab.cardiograph.bottombar.impl.presentation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import tech.gelab.cardiograph.bottombar.impl.domain.BottomNavigationEvent

@Composable
fun DestinationChangedEffect(
    navController: NavController,
    onDestinationChanged: (BottomNavigationEvent.GraphDestinationChanged) -> Unit
) {
    DisposableEffect(navController) {
        val destinationChangedListener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                onDestinationChanged(
                    BottomNavigationEvent.GraphDestinationChanged(
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