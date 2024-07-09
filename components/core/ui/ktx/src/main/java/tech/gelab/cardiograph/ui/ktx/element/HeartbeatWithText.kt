package tech.gelab.cardiograph.ui.ktx.element

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import tech.gelab.cardiograph.ui.ktx.R
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun HeartbeatWithText(modifier: Modifier = Modifier, text: String) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = R.drawable.icon_heartbeat), contentDescription = null)
        Text(
            modifier = Modifier.padding(start = MaterialTheme.spacing.small),
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}