package tech.gelab.cardiograph.ui.ktx.element

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun RationaleImageView(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int,
    @StringRes questionText: Int? = null,
    @StringRes text: Int,
    @StringRes featureText1: Int,
    @StringRes featureText2: Int
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null
        )
        FeatureEnableContent(
            // TODO
            questionText = if (questionText != null) stringResource(id = questionText) else null,
            text = stringResource(id = text),
            featureText1 = stringResource(featureText1),
            featureText2 = stringResource(featureText2)
        )
    }
}