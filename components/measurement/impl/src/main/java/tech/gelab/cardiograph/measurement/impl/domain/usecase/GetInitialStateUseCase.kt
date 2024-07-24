package tech.gelab.cardiograph.measurement.impl.domain.usecase

import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementScreenState
import tech.gelab.cardiograph.ui.topbar.TopBarState
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(private val resourceProvider: ResourceProvider) {

    fun invoke(): MeasurementScreenState {
        return MeasurementScreenState(
            topBarState = TopBarState(
                titleId = R.string.title_prepare_measure,
                showBackButton = true
            ),
            supportingText = resourceProvider.getString(R.string.text_check_signal_quality)
        )
    }

}