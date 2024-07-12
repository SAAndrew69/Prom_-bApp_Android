package tech.gelab.cardiograph.measurement.impl

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import tech.gelab.cardiograph.measurement.impl.presentation.MeasurementScreen
import tech.gelab.cardiograph.measurement.impl.presentation.MeasurementViewModel
import timber.log.Timber
import javax.inject.Inject

class MeasurementFeatureEntryImpl @Inject constructor(
    private val bottomBarFeatureEntry: BottomNavigationFeatureEntry
) : MeasurementFeatureEntry,
    FeatureEventHandler<MeasurementFeatureEvent> {

    private var globalNavController: NavController? = null

    override fun start(): String {
        return ROUTE.name
    }

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun NavGraphBuilder.bottomSheet(navController: NavController) {
        bottomSheet(route = ROUTE.name) {
            this@MeasurementFeatureEntryImpl.globalNavController = LocalNavHostProvider.current
            MeasurementScreen(viewModel = hiltViewModel(
                creationCallback = { factory: MeasurementViewModel.Factory ->
                    factory.create(this@MeasurementFeatureEntryImpl)
                }
            ))
        }
    }

    override fun obtainEvent(event: MeasurementFeatureEvent) {
        when (event) {
            MeasurementFeatureEvent.StartAgain -> globalNavController?.navigate(bottomBarFeatureEntry.start()) {
                globalNavController?.graph?.findStartDestination()?.parent?.route?.let {
                    // TODO debug
                    Timber.d("obtainEvent: popBackStack to route = $it")
                    popUpTo(route = it) {
                        inclusive = true
                    }
                }
            }
        }
    }
}