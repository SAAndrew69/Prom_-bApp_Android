package tech.gelab.cardiograph.ui.dialog.dialognavprovider

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.NavigationRoute
import tech.gelab.cardiograph.ui.dialog.CardioAppDialog
import tech.gelab.cardiograph.ui.dialog.R
import javax.inject.Inject

private const val dialogTitleArgName = "title"
private const val dialogMessageArgName = "message"


interface TextDialogFeatureEntry : ComposableFeatureEntry {

    override val ROUTE: NavigationRoute
        get() = NavigationRoute.TEXT_DIALOG

    fun start(title: String?, message: String?) : String

}

class TextDialogFeatureEntryImpl @Inject constructor() : TextDialogFeatureEntry {

    private val textDialogArgs = listOf(
        navArgument(dialogTitleArgName) {
            type = NavType.StringType
            nullable = true
        },
        navArgument(dialogMessageArgName) {
            type = NavType.StringType
            nullable = true
        }
    )

    override fun start(title: String?, message: String?) : String {
        return "${ROUTE.name}?$dialogTitleArgName=$title&$dialogMessageArgName=$message"
    }

    override fun NavGraphBuilder.composable(navController: NavController) {
        dialog(
            route = "${ROUTE.name}?$dialogTitleArgName={$dialogTitleArgName}&$dialogMessageArgName={$dialogMessageArgName}",
            arguments = textDialogArgs
        ) { navBackStackEntry ->
            val title = navBackStackEntry.arguments?.getString(dialogTitleArgName)
            val message = navBackStackEntry.arguments?.getString(dialogMessageArgName)
            CardioAppDialog(
                title = title,
                text = message,
                buttonLabel = stringResource(id = R.string.label_close),
                onClickButton = { navController.popBackStack() }
            )
        }
    }
}