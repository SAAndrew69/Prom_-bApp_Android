package tech.gelab.cardiograph.idpicker.impl

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.idpicker.api.IdentifierPickerFeatureEntry
import tech.gelab.cardiograph.idpicker.impl.presentation.compose.IdentifierPickerScreen
import tech.gelab.cardiograph.idpicker.impl.presentation.viewmodel.IdentifierPickerViewModel
import tech.gelab.cardiograph.measurement.api.ProgressNavigationFeatureEntry
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject

class IdentifierPickerFeatureEntryImpl @Inject constructor(
    private val progressNavigationFeatureEntry: ProgressNavigationFeatureEntry,
    private val pairingFeatureEntry: PairingFeatureEntry
) : IdentifierPickerFeatureEntry, FeatureEventHandler<IdentifierFeatureEvent> {

    private var navController: NavController? = null

    override fun NavGraphBuilder.composable(navController: NavController) {
        composable(route = ROUTE.name) {
            // TODO make navigation with deeplinks
            this@IdentifierPickerFeatureEntryImpl.navController = LocalNavHostProvider.current
            IdentifierPickerScreen(
                viewModel = hiltViewModel(
                    creationCallback = { factory: IdentifierPickerViewModel.Factory ->
                        factory.create(this@IdentifierPickerFeatureEntryImpl)
                    })
            )
        }
    }

    override fun obtainEvent(event: IdentifierFeatureEvent) {
        when (event) {
            IdentifierFeatureEvent.ConnectDevice -> navController?.navigate(
                pairingFeatureEntry.getSearchRoute(
                    true
                )
            )

            IdentifierFeatureEvent.StartMeasure -> navController?.navigate(
                progressNavigationFeatureEntry.start()
            )
        }
    }

}