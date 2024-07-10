package tech.gelab.cardiograph.pairing.impl.presentation

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import tech.gelab.cardiograph.bridge.api.CardioBleScanner
import tech.gelab.cardiograph.pairing.impl.R
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun DeviceView(
    modifier: Modifier = Modifier,
    discoveredDevice: CardioBleScanner.DiscoveredDevice,
    onDeviceClick: (CardioBleScanner.DiscoveredDevice) -> Unit,
) {
    Row(modifier = modifier
        .clickable { onDeviceClick(discoveredDevice) }
        .padding(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = R.drawable.icon_heartbeat), contentDescription = null)
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(text = discoveredDevice.name)
    }
}

@Preview
@Composable
fun DeviceViewPrev() {
//    DeviceView(
//        discoveredDevice = CardioBleScanner.DiscoveredDevice(
//            "00:00:00:00:00:00",
//            "Device 123456789101112",
//            0,
//            BluetoothDevice()
//        ), onDeviceClick = {})
}