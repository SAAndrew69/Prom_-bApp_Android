package tech.gelab.cardiograph.progressnavigation.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.measurement.progressnavigation.R
import tech.gelab.cardiograph.ui.ktx.element.CardioAppButton
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ElectrodeConnectionScreen(onNextClick: () -> Unit) {
    ElectrodeConnectionView(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.small),
        onNextClick = onNextClick
    )
}

@Composable
fun ElectrodeConnectionView(modifier: Modifier = Modifier, onNextClick: () -> Unit) {
    Box(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            text = stringResource(R.string.label_connection_tip),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = MaterialTheme.spacing.large,
                    top = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.large,
                    bottom = MaterialTheme.spacing.extraLarge
                ),
            painter = painterResource(id = R.drawable.image_electrode_connection),
            contentDescription = null
        )
        CardioAppButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            text = stringResource(R.string.label_next),
            onClick = onNextClick
        )
    }

//    Layout(
//        modifier = modifier,
//        content = {
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                text = stringResource(R.string.label_connection_tip),
//                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
//            )
//            Image(
//                modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.medium),
//                painter = painterResource(id = R.drawable.image_electrode_connection),
//                contentDescription = null
//            )
//            CardioAppButton(
//                modifier = Modifier.fillMaxWidth(),
//                text = stringResource(R.string.label_next),
//                onClick = onNextClick
//            )
//        }
//    ) { measurables, constraints ->
//        check(measurables.size == 3)
//        val placeables = measurables.map {
//            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
//        }
//
//        val text = placeables[0]
//        val image = placeables[1]
//        val button = placeables[2]
//
//        val textHeight = text.height
//        val buttonHeight = button.height
//
//        layout(constraints.maxWidth, constraints.minHeight) {
//            text.place(0, 0)
//            button.place(0, constraints.maxHeight - buttonHeight)
//            image.place(0,0)
//        }
//    }
}

@Preview
@Composable
private fun ElectrodeConnectionViewPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ElectrodeConnectionView(
                modifier = Modifier.padding(MaterialTheme.spacing.small),
                onNextClick = {})
        }
    }
}