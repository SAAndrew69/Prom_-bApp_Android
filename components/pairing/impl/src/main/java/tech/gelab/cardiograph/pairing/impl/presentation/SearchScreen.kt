package tech.gelab.cardiograph.pairing.impl.presentation

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.bridge.api.CardioBleScanner
import tech.gelab.cardiograph.pairing.impl.R
import tech.gelab.cardiograph.pairing.impl.domain.SearchEvent
import tech.gelab.cardiograph.pairing.impl.domain.SearchState
import tech.gelab.cardiograph.ui.ktx.element.CardioButton
import tech.gelab.cardiograph.ui.ktx.element.CardioTextButton
import tech.gelab.cardiograph.ui.ktx.element.RationaleImageView
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState

@Composable
fun SearchScreen(goBackAvailable: Boolean, viewModel: SearchScreenViewModel = hiltViewModel()) {
    val viewState by viewModel.viewStates().collectAsState()

    Column(Modifier.fillMaxSize()) {
        CardioAppBar(
            topBarState = TopBarState(
                R.string.title_device_search,
                showBackButton = goBackAvailable
            ),
            onBackButtonClick = { viewModel.obtainEvent(SearchEvent.BackButtonClick) }
        )
        ScannerView(Modifier.fillMaxSize(), viewState, viewModel::obtainEvent)
    }
}

@Composable
fun ScannerView(
    modifier: Modifier = Modifier,
    scannerScreenState: SearchState,
    onEvent: (SearchEvent) -> Unit,
) {
    Column(modifier) {
        when (scannerScreenState) {
            is SearchState.Stopped -> StoppedView(
                modifier = Modifier.weight(1f),
                viewState = scannerScreenState,
                onRestartScan = onEvent
            )

            is SearchState.NotReady -> NotReadyView(
                modifier = Modifier.weight(1f),
                viewState = scannerScreenState,
                onEvent = onEvent
            )

            is SearchState.Scanning -> ScanningView(
                modifier = Modifier
                    .weight(1f)
                    .padding(MaterialTheme.spacing.small),
                viewState = scannerScreenState,
                onEvent = onEvent
            )

            SearchState.Ready -> {}
        }
        CardioTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_skip_connection),
            onClick = { onEvent(SearchEvent.SkipClick) }
        )
    }
}

@Composable
fun StoppedView(
    modifier: Modifier = Modifier,
    viewState: SearchState.Stopped,
    onRestartScan: (SearchEvent.RestartScanClick) -> Unit,
) {
    Column(
        modifier.padding(
            start = MaterialTheme.spacing.small,
            top = MaterialTheme.spacing.extraLarge,
            end = MaterialTheme.spacing.small
        ),
    ) {
        Column(
            modifier,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(R.drawable.bluetooth_disabled),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = viewState.message,
                    textAlign = TextAlign.Justify
                )
            }
            CardioButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_scan_restart),
                onClick = { onRestartScan(SearchEvent.RestartScanClick) })
        }
    }
}

@Composable
fun NotReadyView(
    modifier: Modifier = Modifier,
    viewState: SearchState.NotReady,
    onEvent: (SearchEvent) -> Unit,
) {
    if (viewState.deniedPermissions.isNotEmpty()) {
        PermissionsDeniedView(
            modifier.padding(
                start = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.extraLarge,
                end = MaterialTheme.spacing.small
            ),
            permissions = viewState.deniedPermissions,
            shouldOpenSettings = viewState.shouldOpenSettings,
            onPermissionsResult = { onEvent(SearchEvent.PermissionRequestResultReceive(it)) }
        )
    } else if (!viewState.bluetoothEnabled) {
        BluetoothDisabledView(
            modifier.padding(
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.small
            )
        )
    } else if (!viewState.locationEnabled) {
        LocationDisabledView(
            modifier.padding(
                start = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.extraLarge,
                end = MaterialTheme.spacing.small
            )
        )
    }
}

@Composable
fun ScanningView(
    modifier: Modifier = Modifier,
    viewState: SearchState.Scanning,
    onEvent: (SearchEvent) -> Unit,
) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
            text = stringResource(R.string.text_scan_choose_device),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Surface(
            modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.extraSmall
        ) {
            LazyColumn(Modifier.fillMaxWidth()) {
                items(viewState.devices) { discoveredDevice ->
                    DeviceView(
                        modifier = Modifier.fillMaxWidth(),
                        discoveredDevice = discoveredDevice,
                        onDeviceClick = { onEvent(SearchEvent.DeviceClick(it)) }
                    )
                }
            }
        }
    }
}

@Composable
fun BluetoothDisabledView(modifier: Modifier = Modifier) {
    val bluetoothActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {  }
    )
    Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
        RationaleImageView(
            imageRes = R.drawable.image_connectivity_bluetooth,
            text = R.string.text_bluetooth_rationale1,
            featureText1 = R.string.text_bluetooth_rationale2,
            featureText2 = R.string.text_bluetooth_rationale3
        )
        CardioButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_enable_bluetooth),
            onClick = { bluetoothActivityResult.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)) })
    }
}

@Composable
fun PermissionsDeniedView(
    modifier: Modifier = Modifier,
    shouldOpenSettings: Boolean,
    permissions: Array<String>,
    onPermissionsResult: (Map<String, Boolean>) -> Unit
) {
    val permissionActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = onPermissionsResult
    )
    val settingsActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {  }
    )
    val context = LocalContext.current

    Column(
        modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.warning),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.text_enable_permissions_rationale),
                textAlign = TextAlign.Justify
            )
        }
        CardioButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(if (shouldOpenSettings) R.string.label_settings else R.string.label_grant_permissions),
            onClick = {
                if (shouldOpenSettings) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.data = uri
                    settingsActivityResult.launch(intent)
                } else {
                    permissionActivityResult.launch(permissions)
                }
            })
    }
}

@Composable
fun LocationDisabledView(modifier: Modifier = Modifier) {
    val settingsActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {  }
    )
    Column(
        modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.location_off),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.text_enable_location_rationale),
                textAlign = TextAlign.Justify
            )
        }
        CardioButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_settings),
            onClick = {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                settingsActivityResult.launch(intent)
            })
    }
}

@Preview
@Composable
private fun NotReadyViewPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ScannerView(
                scannerScreenState = SearchState.NotReady(
                    bluetoothEnabled = true,
                    locationEnabled = false,
                    deniedPermissions = arrayOf()
                )
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun ScanningViewPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
//            ScannerView(
//                scannerScreenState = SearchState.Scanning(
//                    devices = persistentListOf(
//                        CardioBleScanner.DiscoveredDevice("", "Test device 1", 0),
//                        CardioBleScanner.DiscoveredDevice("", "Test device 2", 0),
//                        CardioBleScanner.DiscoveredDevice("", "Test device 3", 0)
//                    )
//                )
//            ) { }
        }
    }
}

@Preview
@Composable
private fun ScanStoppedPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ScannerView(scannerScreenState = SearchState.Stopped("Сканирование приостановлено")) {

            }
        }
    }
}