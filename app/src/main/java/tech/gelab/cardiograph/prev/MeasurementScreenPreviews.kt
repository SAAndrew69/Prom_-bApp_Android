package tech.gelab.cardiograph.prev

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.measurement.api.BluetoothQuality
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.BottomSheetState
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementScreenState
import tech.gelab.cardiograph.measurement.impl.presentation.compose.MeasurementView
import tech.gelab.cardiograph.ui.cardiogram.CardiogramModel
import tech.gelab.cardiograph.ui.cardiogram.MultipleCardiogramValue
import tech.gelab.cardiograph.ui.cardiogram.MultipleCardiogramValueProducer
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.topbar.TopBarState
import kotlin.random.Random

@Preview
@Composable
private fun NoBottomSheetPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val multipleCardiogramValueProducer = MultipleCardiogramValueProducer(
                models = listOf(
                    CardiogramModel("I", "I", 100),
                    CardiogramModel("II", "II", 100)
                )
            )
            for (i in 0..50) {
                multipleCardiogramValueProducer.produceValue(
                    persistentListOf(
                        MultipleCardiogramValue("I", Random.nextFloat()*2000),
                        MultipleCardiogramValue("II", Random.nextFloat()*2000),
                    )
                )
            }
            MeasurementView(
                viewState = MeasurementScreenState(
                    topBarState = TopBarState(
                        R.string.title_prepare_measure,
                        showBackButton = true
                    ),
                    supportingText = stringResource(id = R.string.text_check_signal_quality),
                    bottomSheetState = null
                ),
                viewAction = null,
                multipleCardiogramValueProducer = multipleCardiogramValueProducer
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun MeasurementBottomSheetPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val multipleCardiogramValueProducer = MultipleCardiogramValueProducer(
                models = listOf(
                    CardiogramModel("I", "I", 100),
                    CardiogramModel("II", "II", 100)
                )
            )
            for (i in 0..50) {
                multipleCardiogramValueProducer.produceValue(
                    persistentListOf(
                        MultipleCardiogramValue("I", Random.nextFloat()*2000),
                        MultipleCardiogramValue("II", Random.nextFloat()*2000),
                    )
                )
            }
            MeasurementView(
                modifier = Modifier.fillMaxSize(),
                viewState = MeasurementScreenState(
                    topBarState = TopBarState(R.string.title_measure),
                    supportingText = stringResource(id = R.string.text_please_wait),
                    bottomSheetState = BottomSheetState.MeasureInProgress(
                        bluetoothQuality = BluetoothQuality.GOOD,
                        employeeId = "ID 1000",
                        measurementNumLabel = "Первое",
                        progressLabel = "1:30",
                        progress = 0.75f
                    )
                ),
                viewAction = null, multipleCardiogramValueProducer = multipleCardiogramValueProducer
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun UploadedBottomSheetPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val multipleCardiogramValueProducer = MultipleCardiogramValueProducer(
                models = listOf(
                    CardiogramModel("I", "I", 100),
                    CardiogramModel("II", "II", 100)
                )
            )
            for (i in 0..50) {
                multipleCardiogramValueProducer.produceValue(
                    persistentListOf(
                        MultipleCardiogramValue("I", Random.nextFloat()*2000),
                        MultipleCardiogramValue("II", Random.nextFloat()*2000),
                    )
                )
            }
            MeasurementView(
                modifier = Modifier.fillMaxSize(),
                viewState = MeasurementScreenState(
                    topBarState = TopBarState(R.string.title_prepare_measure),
                    supportingText = stringResource(id = R.string.text_please_wait),
                    bottomSheetState = BottomSheetState.UploadSuccess
                ),
                viewAction = null, multipleCardiogramValueProducer = multipleCardiogramValueProducer
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun ExerciseBottomSheet() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val multipleCardiogramValueProducer = MultipleCardiogramValueProducer(
                models = listOf(
                    CardiogramModel("I", "I", 100),
                    CardiogramModel("II", "II", 100)
                )
            )
            for (i in 0..50) {
                multipleCardiogramValueProducer.produceValue(
                    persistentListOf(
                        MultipleCardiogramValue("I", Random.nextFloat()*2000),
                        MultipleCardiogramValue("II", Random.nextFloat()*2000),
                    )
                )
            }
            MeasurementView(
                modifier = Modifier.fillMaxSize(),
                viewState = MeasurementScreenState(
                    topBarState = TopBarState(
                        titleId = R.string.title_measure,
                        showBackButton = false
                    ),
                    supportingText = stringResource(id = R.string.text_exercise),
                    bottomSheetState = BottomSheetState.SecondMeasurePreparation(
                        progressLabel = "0:05",
                        progress = 0.95f,
                        runningOut = true
                    )
                ),
                viewAction = null, multipleCardiogramValueProducer = multipleCardiogramValueProducer
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun MeasureSuccessPrev() {
    CardiographAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val multipleCardiogramValueProducer = MultipleCardiogramValueProducer(
                models = listOf(
                    CardiogramModel("I", "I", 100),
                    CardiogramModel("II", "II", 100)
                )
            )
            for (i in 0..50) {
                multipleCardiogramValueProducer.produceValue(
                    persistentListOf(
                        MultipleCardiogramValue("I", Random.nextFloat()*2000),
                        MultipleCardiogramValue("II", Random.nextFloat()*2000),
                    )
                )
            }
            MeasurementView(
                modifier = Modifier.fillMaxSize(),
                viewState = MeasurementScreenState(
                    topBarState = TopBarState(R.string.title_prepare_measure),
                    supportingText = stringResource(id = R.string.text_please_wait),
                    bottomSheetState = BottomSheetState.MeasureSuccess
                ),
                viewAction = null, multipleCardiogramValueProducer = multipleCardiogramValueProducer
            ) {

            }
        }
    }
}