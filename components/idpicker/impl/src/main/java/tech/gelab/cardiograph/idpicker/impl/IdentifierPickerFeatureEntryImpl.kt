package tech.gelab.cardiograph.idpicker.impl

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.idpicker.api.IdentifierPickerFeatureEntry
import tech.gelab.cardiograph.idpicker.api.NewEmployeeFeatureEntry
import tech.gelab.cardiograph.idpicker.impl.presentation.IdentifierPickerScreen
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IdentifierPickerFeatureEntryImpl @Inject constructor(
    private val measurementFeatureEntry: MeasurementFeatureEntry,
    private val electrodeConnectionFeatureEntry: ElectrodeConnectionFeatureEntry,
    private val pairingFeatureEntry: PairingFeatureEntry,
    private val newEmployeeFeatureEntry: NewEmployeeFeatureEntry,
    private val measurementApi: MeasurementApi
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
                if (measurementApi.shouldShowElectrodeConnectionTip())
                    electrodeConnectionFeatureEntry.start(
                        nextDestinationRoute = measurementFeatureEntry.start(),
                        showCheckBox = true
                    )
                else measurementFeatureEntry.start()
            )

            IdentifierFeatureEvent.CreateNewEmployeeRecord -> navController?.navigate(
                // TODO get id
                newEmployeeFeatureEntry.start(1234)
            )
        }
    }
}