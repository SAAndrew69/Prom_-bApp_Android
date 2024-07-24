package tech.gelab.cardiograph.measurement.impl.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import tech.gelab.cardiograph.measurement.impl.R

@Composable
fun SuccessBottomSheet(modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            modifier = Modifier.align(Alignment.TopStart),
            text = stringResource(id = R.string.text_measure_success)
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.image_measure_success),
            contentDescription = null
        )
    }
}