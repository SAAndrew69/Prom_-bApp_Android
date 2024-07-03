package tech.gelab.cardiograph.scanner.impl.presentation

import android.bluetooth.BluetoothAdapter
import android.content.ActivityNotFoundException
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.impl.R
import tech.gelab.cardiograph.scanner.impl.model.ScannerScreenState
import tech.gelab.cardiograph.ui.theme.spacing
import timber.log.Timber

@Composable
fun ScannerScreen(scannerScreenViewModel: ScannerScreenViewModel) {

    val scannerScreenState by scannerScreenViewModel.scannerScreenStateFlow.collectAsState()

    Column {
        when (scannerScreenState) {
            is ScannerScreenState.Stopped -> StoppedView(
                state = scannerScreenState as ScannerScreenState.Stopped,
                onRestartScanClick = scannerScreenViewModel::startScan
            )

            is ScannerScreenState.NotReady -> NotReadyView(
                state = scannerScreenState as ScannerScreenState.NotReady,
                processPermissionActivityResult = scannerScreenViewModel::onPermissionActivityResult,
                processBluetoothActivityResult = scannerScreenViewModel::onBluetoothActivityResult
            )

            is ScannerScreenState.Scanning -> ScanningView(
                state = scannerScreenState as ScannerScreenState.Scanning,
                onDevicePick =
            )
        }

    }
}

@Composable
fun StoppedView(state: ScannerScreenState.Stopped, onRestartScanClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = state.message)
        TextButton(onClick = onRestartScanClick) {
            Text(text = stringResource(R.string.label_scan_restart))
        }
    }
}

@Composable
fun NotReadyView(
    state: ScannerScreenState.NotReady,
    processPermissionActivityResult: (Map<String, Boolean>) -> Unit,
    processBluetoothActivityResult: (ActivityResult) -> Unit,
) {

    val permissionActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = processPermissionActivityResult
    )

    val bluetoothActivityResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = processBluetoothActivityResult
    )

    val context = LocalContext.current

    LaunchedEffect(key1 = state.deniedPermissions) {
        if (state.deniedPermissions.isNotEmpty()) {
            permissionActivityResult.launch(state.deniedPermissions)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!state.bluetoothEnabled) {
            RationaleView(
                iconId = R.drawable.bluetooth_disabled,
                textId = R.string.text_enable_bluetooth_rationale,
                labelId = R.string.label_enable_bluetooth,
                onButtonClick = { bluetoothActivityResult.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)) })
        } else if (!state.locationEnabled) {
            RationaleView(
                iconId = R.drawable.location_off,
                textId = R.string.text_enable_location_rationale,
                labelId = R.string.label_settings,
                onButtonClick = {
                    try {
                        val locSettings = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        context.startActivity(locSettings)
                    } catch (anfe: ActivityNotFoundException) {
                        Timber.e("Settings activity not found")
                    }
                })
        } else if (state.deniedPermissions.isNotEmpty()) {
            Surface {
                Column(Modifier.padding(MaterialTheme.spacing.small)) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.warning),
                            contentDescription = null
                        )
                        Text(text = stringResource(R.string.text_enable_permissions_rationale))
                    }
                    TextButton(onClick = { permissionActivityResult.launch(state.deniedPermissions) }) {
                        Text(text = stringResource(R.string.label_grant_permissions))
                    }
                }
            }
        }
    }
}

@Composable
fun ScanningView(
    state: ScannerScreenState.Scanning,
    onDevicePick: (ScannerApi.DiscoveredDevice) -> Unit,
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(state.discoveredDevices) { discoveredDevice ->
            DeviceView(discoveredDevice = discoveredDevice, onDeviceClick = onDevicePick)
        }
    }
}

@Composable
fun RationaleView(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes textId: Int,
    @StringRes labelId: Int,
    onButtonClick: () -> Unit,
) {
    Column(modifier) {
        Icon(painter = painterResource(id = iconId), contentDescription = null)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(text = stringResource(textId), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        TextButton(onClick = onButtonClick) {
            Text(text = stringResource(id = labelId))
        }
    }
}

