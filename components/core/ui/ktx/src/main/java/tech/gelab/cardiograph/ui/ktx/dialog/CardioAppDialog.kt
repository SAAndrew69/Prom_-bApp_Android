package tech.gelab.cardiograph.ui.ktx.dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import tech.gelab.cardiograph.ui.ktx.R
import tech.gelab.cardiograph.ui.ktx.element.CardioAppTextButton
import tech.gelab.cardiograph.ui.ktx.element.letCompose
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
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
    @StringRes buttonLabelId: Int,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes imageId: Int? = null,
    @StringRes titleId: Int? = null,
    imageComposable: (@Composable () -> Unit)? = imageId?.letCompose { imageIdNotNullable ->
        Image(
            painter = painterResource(imageIdNotNullable),
            contentDescription = titleId?.let { stringResource(it) }
        )
    },
    titleComposable: (@Composable () -> Unit)? = titleId?.letCompose { titleIdNotNullable ->
        Text(
            text = stringResource(titleIdNotNullable),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    },
    @StringRes textId: Int? = null,
    textComposable: (@Composable () -> Unit)? = textId?.letCompose {
        Text(
            text = stringResource(textId),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    },
    buttonComposable: @Composable () -> Unit = {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.spacing.small), horizontalArrangement = Arrangement.End) {
            CardioAppTextButton(
                text = stringResource(buttonLabelId),
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

@Composable
fun CardioAppDialog(
    modifier: Modifier = Modifier,
    buttonLabel: String,
    onClickButton: () -> Unit,
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
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.spacing.small), horizontalArrangement = Arrangement.End) {
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

@Preview
@Composable
private fun DialogPreview() {
    CardiographAppTheme {
        CardioAppDialog(
            buttonLabelId = R.string.test_button_label,
            onClickButton = { /*TODO*/ },
            titleId = R.string.test_dialog_title,
            textId = R.string.test_dialog_text
        )
    }
}