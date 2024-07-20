package tech.gelab.cardiograph.measurement.impl.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun DataUploadBottomSheet(modifier: Modifier = Modifier, onStartAgainClick: () -> Unit) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(R.string.text_wait_upload))
        CircularProgressIndicator()
        StartAgainButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MaterialTheme.spacing.large),
            onClick = onStartAgainClick
        )
    }
}