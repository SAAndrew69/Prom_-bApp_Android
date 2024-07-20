package tech.gelab.measurement.electrodetip

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import javax.inject.Inject

class ElectrodeConnectionFeatureEntryImpl @Inject constructor() : ElectrodeConnectionFeatureEntry,
    FeatureEventHandler<CloseDialogFeatureEvent> {

    companion object {
        const val NEXT_DESTINATION = "next_destination"
        const val SHOW_CHECKBOX = "show_checkbox"
    }

    private val arguments = listOf(
        navArgument(NEXT_DESTINATION) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        },
        navArgument(SHOW_CHECKBOX) {
            type = NavType.BoolType
            nullable = false
            defaultValue = true
        }
    )

    private var globalNavController: NavController? = null


    override fun start(nextDestinationRoute: String?, showCheckBox: Boolean): String {
        return "$ROUTE?$NEXT_DESTINATION=$nextDestinationRoute&$SHOW_CHECKBOX=$showCheckBox"
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        dialog(
            route = "${ROUTE}?$NEXT_DESTINATION={$NEXT_DESTINATION}&$SHOW_CHECKBOX={$SHOW_CHECKBOX}",
            arguments = arguments
        ) {
            ElectrodeConnectionDialog()
        }
    }

    override fun obtainEvent(event: CloseDialogFeatureEvent) {
        val route = event.nextDestination
        if (route == null) {
            globalNavController?.popBackStack()
        } else {
            globalNavController?.navigate(route)
        }
    }
}