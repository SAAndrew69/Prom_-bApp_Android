package tech.gelab.cardiograph.measurement.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.animatedComposable
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import tech.gelab.cardiograph.measurement.impl.presentation.compose.MeasurementScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasurementFeatureEntryImpl @Inject constructor(
    private val bottomBarFeatureEntry: BottomNavigationFeatureEntry,
    private val electrodeConnectionFeatureEntry: ElectrodeConnectionFeatureEntry
) : MeasurementFeatureEntry,
    FeatureEventHandler<MeasurementFeatureEvent> {

    private var globalNavController: NavController? = null

    override fun start(): String {
        return ROUTE.name
    }

    private fun popUpToBottomScreen() {
        val popUpResult = globalNavController?.popBackStack(
            route = bottomBarFeatureEntry.start(), inclusive = false
        )
        if (popUpResult == false) {
            // TODO replace with restarting activity
            globalNavController?.navigate(
                bottomBarFeatureEntry.start()
            )
        }
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        animatedComposable(route = ROUTE.name) {
            this@MeasurementFeatureEntryImpl.globalNavController = LocalNavHostProvider.current
            MeasurementScreen()
        }
    }

    override fun obtainEvent(event: MeasurementFeatureEvent) {
        when (event) {
            MeasurementFeatureEvent.Restart -> popUpToBottomScreen()

            MeasurementFeatureEvent.OpenInfoDialog -> globalNavController?.navigate(
                electrodeConnectionFeatureEntry.start(
                    nextDestinationRoute = null,
                    showCheckBox = false
                )
            )

            MeasurementFeatureEvent.GoBack -> popUpToBottomScreen()
        }
    }
}