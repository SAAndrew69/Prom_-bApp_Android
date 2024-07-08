package tech.gelab.cardiograph.ui.ktx.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tech.gelab.cardiograph.ui.theme.spacing

// TODO apply right modifiers
@Composable
fun CardioAppDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    image: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier.padding(
            top = MaterialTheme.spacing.small,
            bottom = MaterialTheme.spacing.small
        ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (image != null) {
            Box {
                image()
            }
        }
        if (title != null) {
            Box(modifier = Modifier.padding(top = MaterialTheme.spacing.small)) {
                title()
            }
        }
        if (text != null) {
            Box(
                modifier = Modifier.padding(
                    start = MaterialTheme.spacing.medium,
                    top = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium
                )
            ) {
                text()
            }
        }

        Box {
            buttons()
        }
    }
}