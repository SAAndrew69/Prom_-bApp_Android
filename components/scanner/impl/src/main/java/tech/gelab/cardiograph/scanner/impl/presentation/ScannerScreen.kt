package tech.gelab.cardiograph.scanner.impl.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.filterNotNull
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.impl.R
import tech.gelab.cardiograph.scanner.impl.domain.model.action.ScannerScreenAction
import tech.gelab.cardiograph.scanner.impl.domain.model.event.ScannerScreenEvent
import tech.gelab.cardiograph.scanner.impl.domain.model.state.ScannerScreenState
import tech.gelab.cardiograph.ui.ktx.element.CardioAppTextButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing
import tech.gelab.cardiograph.ui.topbar.CardioAppBar
import tech.gelab.cardiograph.ui.topbar.TopBarState
import timber.log.Timber

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
    Column(modifier, verticalArrangement = Arrangement.SpaceBetween) {
        when (scannerScreenState) {
            is ScannerScreenState.Stopped -> StoppedView(
                viewState = scannerScreenState,
                onEvent = onEvent
            )

            is ScannerScreenState.NotReady -> NotReadyView(
                viewState = scannerScreenState,
                onEvent = onEvent
            )

            is ScannerScreenState.Scanning -> ScanningView(
                viewState = scannerScreenState,
                onEvent = onEvent
            )

            ScannerScreenState.Ready -> {}
        }
        CardioAppTextButton(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.label_skip_connection),
            onClick = { onEvent(ScannerScreenEvent.SkipClick) }
        )
    }
}

@Composable
fun StoppedView(
    modifier: Modifier = Modifier,
    viewState: ScannerScreenState.Stopped,
    onEvent: (ScannerScreenEvent) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = viewState.message)
        TextButton(onClick = { onEvent(ScannerScreenEvent.RestartScanClick) }) {
            Text(text = stringResource(R.string.label_scan_restart))
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

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!viewState.bluetoothEnabled) {
            RationaleView(
                iconId = R.drawable.bluetooth_disabled,
                textId = R.string.text_enable_bluetooth_rationale,
                labelId = R.string.label_enable_bluetooth,
                onButtonClick = {
//                    bluetoothActivityResult.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                })
        } else if (!viewState.locationEnabled) {
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
        } else if (viewState.deniedPermissions.isNotEmpty()) {
            Surface {
                Column(Modifier.padding(MaterialTheme.spacing.small)) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.warning),
                            contentDescription = null
                        )
                        Text(text = stringResource(R.string.text_enable_permissions_rationale))
                    }
                    TextButton(onClick = {
//                        permissionActivityResult.launch(state.deniedPermissions)
                    }) {
                        Text(text = stringResource(R.string.label_grant_permissions))
                    }
                }
            }
        }
    }
}

@Composable
fun ScanningView(
    modifier: Modifier = Modifier,
    viewState: ScannerScreenState.Scanning,
    onEvent: (ScannerScreenEvent) -> Unit,
) {
    Surface(
        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        LazyColumn(modifier.fillMaxWidth()) {
            items(viewState.discoveredDevices) { discoveredDevice ->
                DeviceView(
                    discoveredDevice = discoveredDevice,
                    onDeviceClick = { onEvent(ScannerScreenEvent.DeviceClick(it)) }
                )
            }
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

@Preview
@Composable
private fun NotReadyViewPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NotReadyView(
                viewState = ScannerScreenState.NotReady(
                    bluetoothEnabled = false,
                    locationEnabled = false,
                    deniedPermissions = arrayOf()
                ),
                onEvent = {}
            )
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