package tech.gelab.cardiograph.pairing.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import tech.gelab.cardiograph.pairing.impl.R
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ConnectionScreen(viewModel: ConnectionScreenViewModel = hiltViewModel()) {
    ConnectionView(modifier = Modifier.fillMaxSize())
}

@Composable
fun ConnectionView(modifier: Modifier = Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator()
        Text(
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
            text = stringResource(R.string.label_connect_device)
        )
    }
}