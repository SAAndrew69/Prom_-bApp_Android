package tech.gelab.cardiograph.idpicker.newemployee

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.compose.navigation
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.core.ui.navigation.LocalNavHostProvider
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.idpicker.api.NewEmployeeFeatureEntry
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import tech.gelab.cardiograph.idpicker.newemployee.presentation.DatePickerDialog
import tech.gelab.cardiograph.idpicker.newemployee.presentation.NewEmployeeScreen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewEmployeeFeatureEntryImpl @Inject constructor(
    private val measurementApi: MeasurementApi,
    private val electrodeConnectionFeatureEntry: ElectrodeConnectionFeatureEntry,
    private val measurementFeatureEntry: MeasurementFeatureEntry
) : NewEmployeeFeatureEntry, FeatureEventHandler<NewEmployeeFeatureEvent> {

    companion object {
        const val ID_ARG = "id"
        const val DATETIME = "datetime"

        private val screenRoute = "${NavigationRoute.NEW_EMPLOYEE}screen?$ID_ARG={$ID_ARG}&$DATETIME={$DATETIME}"
        private val datePickerRoute = "${NavigationRoute.NEW_EMPLOYEE}datepicker"
    }

    private val arguments = listOf(
        navArgument(ID_ARG) {
            type = NavType.IntType
            nullable = false
        },
        navArgument(DATETIME) {
            type = NavType.LongType
            defaultValue = 0
        }
    )

    private var navController: NavController? = null

    override fun start(id: Int): String {
        return "$ROUTE?$ID_ARG=$id&$DATETIME=0"
    }

    override fun NavGraphBuilder.navigation(navController: NavController) {
        navigation(route = ROUTE.name, startDestination = "") {
            composable(route = screenRoute, arguments = arguments) {
                this@NewEmployeeFeatureEntryImpl.navController = LocalNavHostProvider.current
                NewEmployeeScreen()
            }
            dialog(route = datePickerRoute) {
                this@NewEmployeeFeatureEntryImpl.navController = LocalNavHostProvider.current
                DatePickerDialog()
            }
        }
    }

    override fun obtainEvent(event: NewEmployeeFeatureEvent) {
        when (event) {
            NewEmployeeFeatureEvent.ProceedToNext -> navController?.navigate(
                if (measurementApi.shouldShowElectrodeConnectionTip()) {
                    electrodeConnectionFeatureEntry.start(
                        nextDestinationRoute = measurementFeatureEntry.start(),
                        showCheckBox = true
                    )
                } else {
                    measurementFeatureEntry.start()
                }
            )
        }
    }
}