package tech.gelab.cardiograph.idpicker.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.idpicker.api.IdentifierPickerFeatureEntry
import tech.gelab.cardiograph.idpicker.impl.presentation.IdentifierPickerScreen
import tech.gelab.cardiograph.measurement.api.ProgressNavigationFeatureEntry
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IdentifierPickerFeatureEntryImpl @Inject constructor(
    private val progressNavigationFeatureEntry: ProgressNavigationFeatureEntry,
    private val pairingFeatureEntry: PairingFeatureEntry
) : IdentifierPickerFeatureEntry, FeatureEventHandler<IdentifierFeatureEvent> {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {
            // TODO make navigation with deeplinks
            this@IdentifierPickerFeatureEntryImpl.navController = LocalNavHostProvider.current
            IdentifierPickerScreen()
        }
    }

    override fun obtainEvent(event: IdentifierFeatureEvent) {
        when (event) {
            IdentifierFeatureEvent.ConnectDevice -> navController?.navigate(
                pairingFeatureEntry.getSearchRoute(
                    goBackAvailable = true,
                    skipAvailable = false
                )
            )

            IdentifierFeatureEvent.StartMeasure -> navController?.navigate(
                progressNavigationFeatureEntry.start()
            )
        }
    }
}