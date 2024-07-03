package tech.gelab.cardiograph.scanner.impl

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navigation
import tech.gelab.cardiograph.core.navigation.NavigationRoute
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import tech.gelab.cardiograph.scanner.impl.domain.ScannerScreenEventHandler
import tech.gelab.cardiograph.scanner.impl.presentation.ScannerScreen
import tech.gelab.cardiograph.scanner.impl.presentation.ScannerScreenViewModel
import javax.inject.Inject

class ScannerFeatureEntryImpl @Inject constructor() : ScannerFeatureEntry,
    ScannerScreenEventHandler {

    companion object {
        const val DEVICE_ADDRESS = "address"
        const val DEVICE_NAME = "name"
    }

    private var globalNavController: NavController? = null

    override fun getScannerRoute(): String {
        TODO("Not yet implemented")
    }

    override fun getConnectionDialog(discoveredDevice: ScannerApi.DiscoveredDevice): String {
        return "${NavigationRoute.CONNECTION_DIALOG.name}?$DEVICE_ADDRESS={${discoveredDevice.address}}&$DEVICE_NAME=${discoveredDevice.name}"
    }

    override fun NavGraphBuilder.navigation(navController: NavController) {

        navigation(route = ROUTE.name, startDestination = NavigationRoute.SCANNER.name, builder = {
            composable(route = NavigationRoute.SCANNER.name) {
                ScannerScreen(
                    scannerScreenViewModel = hiltViewModel(
                        creationCallback = { factory: ScannerScreenViewModel.Factory ->
                            factory.create(this@ScannerFeatureEntryImpl)
                        })
                )
            }
            dialog(route = "${NavigationRoute.CONNECTION_DIALOG.name}?$DEVICE_ADDRESS={$DEVICE_ADDRESS}&$DEVICE_NAME=${DEVICE_NAME}") {

            }
        })

    }

    override fun onNextClick(discoveredDevice: ScannerApi.DiscoveredDevice) {
        TODO("Not yet implemented")
    }

    override fun onSkipClick() {
        TODO("Not yet implemented")
    }
}