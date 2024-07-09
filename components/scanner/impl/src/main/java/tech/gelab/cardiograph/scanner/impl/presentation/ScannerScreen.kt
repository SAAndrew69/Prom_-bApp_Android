package tech.gelab.cardiograph.scanner.impl.presentation

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
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.flow.filterNotNull
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.impl.R
import tech.gelab.cardiograph.scanner.impl.domain.model.action.ScannerScreenAction
import tech.gelab.cardiograph.scanner.impl.domain.model.event.ScannerScreenEvent
import tech.gelab.cardiograph.scanner.impl.domain.model.state.ScannerScreenState
import tech.gelab.cardiograph.ui.ktx.element.CardioAppButton
import tech.gelab.cardiograph.ui.ktx.element.CardioAppTextButton
import tech.gelab.cardiograph.ui.ktx.element.RationaleImageView
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState

@Composable
fun ScannerScreen(
    goBackAvailable: Boolean,
    viewModel: ScannerScreenViewModel = hiltViewModel(),
    onScannerScreenAction: (ScannerScreenAction) -> Unit,
) {
    val scannerScreenState by viewModel.viewStates().collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.viewActions()
            .filterNotNull()
            .collect(onScannerScreenAction)
    }

    Column(Modifier.fillMaxSize()) {
        CardioAppBar(
            topBarState = TopBarState(
                R.string.title_device_search,
                showBackButton = goBackAvailable
            ),
            onBackButtonClick = { viewModel.obtainEvent(ScannerScreenEvent.GoBack) }
        )
        ScannerView(Modifier.fillMaxSize(), scannerScreenState, viewModel::obtainEvent)
    }
}

@Composable
fun ScannerView(
    modifier: Modifier = Modifier,
    scannerScreenState: ScannerScreenState,
    onEvent: (ScannerScreenEvent) -> Unit,
) {
    Column(modifier) {
        when (scannerScreenState) {
            is ScannerScreenState.Stopped -> StoppedView(
                modifier = Modifier.weight(1f),
                viewState = scannerScreenState,
                onRestartScan = onEvent
            )

            is ScannerScreenState.NotReady -> NotReadyView(
                modifier = Modifier.weight(1f),
                viewState = scannerScreenState,
                onEvent = onEvent
            )

            is ScannerScreenState.Scanning -> ScanningView(
                modifier = Modifier
                    .weight(1f)
                    .padding(MaterialTheme.spacing.small),
                viewState = scannerScreenState,
                onEvent = onEvent
            )

            ScannerScreenState.Ready -> {}
        }
        CardioAppTextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_skip_connection),
            onClick = { onEvent(ScannerScreenEvent.SkipClick) }
        )
    }
}

@Composable
fun StoppedView(
    modifier: Modifier = Modifier,
    viewState: ScannerScreenState.Stopped,
    onRestartScan: (ScannerScreenEvent.RestartScanClick) -> Unit,
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
            CardioAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.label_scan_restart),
                onClick = { onRestartScan(ScannerScreenEvent.RestartScanClick) })
        }
    }
}

@Composable
fun NotReadyView(
    modifier: Modifier = Modifier,
    viewState: ScannerScreenState.NotReady,
    onEvent: (ScannerScreenEvent) -> Unit,
) {
//
//    val permissionActivityResult = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions(),
//        onResult = processPermissionActivityResult
//    )
//
//    val bluetoothActivityResult = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult(),
//        onResult = processBluetoothActivityResult
//    )

    val context = LocalContext.current

    LaunchedEffect(key1 = viewState.deniedPermissions) {
        if (viewState.deniedPermissions.isNotEmpty()) {
//            permissionActivityResult.launch(state.deniedPermissions)
        }
    }

    if (viewState.deniedPermissions.isNotEmpty()) {
        PermissionsDeniedView(
            modifier.padding(
                start = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.extraLarge,
                end = MaterialTheme.spacing.small
            ),
            permissions = viewState.deniedPermissions,
            shouldOpenSettings = viewState.shouldOpenSettings,
            onPermissionsResult = onEvent
        )
    } else if (!viewState.bluetoothEnabled) {
        BluetoothDisabledView(
            modifier.padding(
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.small
            ), onEvent
        )
    } else if (!viewState.locationEnabled) {
        LocationDisabledView(
            modifier.padding(
                start = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.extraLarge,
                end = MaterialTheme.spacing.small
            ), onLocationRequestResult = onEvent
        )
    }
}

@Composable
fun ScanningView(
    modifier: Modifier = Modifier,
    viewState: ScannerScreenState.Scanning,
    onEvent: (ScannerScreenEvent) -> Unit,
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
                items(viewState.discoveredDevices) { discoveredDevice ->
                    DeviceView(
                        modifier = Modifier.fillMaxWidth(),
                        discoveredDevice = discoveredDevice,
                        onDeviceClick = { onEvent(ScannerScreenEvent.DeviceClick(it)) }
                    )
                }
            }
        }
    }
}

@Composable
fun BluetoothDisabledView(
    modifier: Modifier = Modifier,
    onBluetoothEnableResult: (ScannerScreenEvent.BluetoothEnableResult) -> Unit
) {
    val bluetoothActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { onBluetoothEnableResult(ScannerScreenEvent.BluetoothEnableResult(it)) }
    )
    Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
        RationaleImageView(
            imageRes = R.drawable.image_connectivity_bluetooth,
            text = R.string.text_bluetooth_rationale1,
            featureText1 = R.string.text_bluetooth_rationale2,
            featureText2 = R.string.text_bluetooth_rationale3
        )
        CardioAppButton(
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
    onPermissionsResult: (ScannerScreenEvent.PermissionsRequestResult) -> Unit
) {
    val permissionActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { onPermissionsResult(ScannerScreenEvent.PermissionsRequestResult(it)) }
    )
    val settingsActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {}
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
        CardioAppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(if (shouldOpenSettings) R.string.label_settings else R.string.label_grant_permissions),
            onClick = {
                if (shouldOpenSettings) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.data = uri
                    context.startActivity(intent)
                } else {
                    permissionActivityResult.launch(permissions)
                }
            })
    }
}

@Composable
fun LocationDisabledView(
    modifier: Modifier = Modifier,
    onLocationRequestResult: (ScannerScreenEvent.LocationRequestResult) -> Unit
) {
    val settingsActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { onLocationRequestResult(ScannerScreenEvent.LocationRequestResult(it)) }
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
        CardioAppButton(
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
                scannerScreenState = ScannerScreenState.NotReady(
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
            ScannerView(
                scannerScreenState = ScannerScreenState.Scanning(
                    discoveredDevices = persistentListOf(
                        ScannerApi.DiscoveredDevice("", "Test device 1", 0),
                        ScannerApi.DiscoveredDevice("", "Test device 2", 0),
                        ScannerApi.DiscoveredDevice("", "Test device 3", 0)
                    )
                )
            ) {

            }
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
            ScannerView(scannerScreenState = ScannerScreenState.Stopped("Сканирование приостановлено")) {

            }
        }
    }
}