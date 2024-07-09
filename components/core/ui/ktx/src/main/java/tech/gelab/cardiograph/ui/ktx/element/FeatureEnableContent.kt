package tech.gelab.cardiograph.ui.ktx.element

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun FeatureEnableContent(
    modifier: Modifier = Modifier,
    questionText: String? = null,
    text: String,
    featureText1: String,
    featureText2: String,
) {
    Column(modifier) {
        if (questionText != null) {
            Text(text = questionText, style = MaterialTheme.typography.labelLarge)
        }
        Text(
            modifier = Modifier.padding(top = MaterialTheme.spacing.small),
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
        HeartbeatWithText(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.extraSmall,
                top = MaterialTheme.spacing.small
            ),
            text = featureText1
        )
        HeartbeatWithText(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.extraSmall,
                top = MaterialTheme.spacing.small
            ),
            text = featureText2
        )
    }
}

//@Preview
//@Composable
//private fun FeatureEnableContentPrev() {
//    CardiographAppTheme {
//        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
//            FeatureEnableContent(
//                questionText = stringResource(R.string.label_skip_question),
//                text = stringResource(id = R.string.text_connectivity_internet_1),
//                featureText1 = stringResource(id = R.string.text_connectivity_internet_2),
//                featureText2 = stringResource(id = R.string.text_connectivity_internet_3)
//            )
//        }
//    }
//}