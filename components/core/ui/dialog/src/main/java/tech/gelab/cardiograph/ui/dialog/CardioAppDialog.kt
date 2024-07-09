package tech.gelab.cardiograph.ui.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import tech.gelab.cardiograph.ui.ktx.element.CardioAppTextButton
import tech.gelab.cardiograph.ui.ktx.element.letCompose
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun CardioAppDialog(
    modifier: Modifier = Modifier,
    buttons: @Composable () -> Unit,
    image: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
    closeOnClickOutside: Boolean = true,
) {
    Dialog(onDismissRequest = {
        if (closeOnClickOutside) {
            onDismissRequest?.invoke()
        }
    }) {
        Box(
            modifier = modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.background)
        ) {
            CardioAppDialogContent(buttons = buttons, image = image, title = title, text = text)
        }
    }
}

@Composable
fun CardioAppDialog(
    buttonLabel: String,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes imageId: Int? = null,
    title: String? = null,
    imageComposable: (@Composable () -> Unit)? = imageId?.letCompose { imageIdNotNullable ->
        Image(
            painter = painterResource(imageIdNotNullable),
            contentDescription = title
        )
    },
    titleComposable: (@Composable () -> Unit)? = title?.letCompose { titleIdNotNullable ->
        Text(
            modifier = Modifier.padding(MaterialTheme.spacing.small),
            text = titleIdNotNullable,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    },
    text: String? = null,
    textComposable: (@Composable () -> Unit)? = text?.letCompose {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    },
    buttonComposable: @Composable () -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.End
        ) {
            CardioAppTextButton(
                text = buttonLabel,
                onClick = onClickButton
            )
        }
    },
    onDismissRequest: (() -> Unit)? = null,
    closeOnClickOutside: Boolean = false,
) {
    CardioAppDialog(
        modifier,
        buttonComposable,
        image = imageComposable,
        title = titleComposable,
        text = textComposable,
        onDismissRequest,
        closeOnClickOutside
    )
}