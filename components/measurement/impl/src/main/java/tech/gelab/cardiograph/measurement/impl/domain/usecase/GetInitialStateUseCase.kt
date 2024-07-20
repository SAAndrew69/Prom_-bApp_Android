package tech.gelab.cardiograph.measurement.impl.domain.usecase

import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.core.util.ResourceProvider
import tech.gelab.cardiograph.measurement.impl.R
import tech.gelab.cardiograph.measurement.impl.domain.MeasurementState
import tech.gelab.cardiograph.ui.topbar.TopBarState

class GetInitialStateUseCase(private val resourceProvider: ResourceProvider) {

    fun invoke(): MeasurementState {
        return MeasurementState(
            topBarState = TopBarState(
                titleId = R.string.title_prepare_measure,
                showBackButton = true
            ),
            supportingText = resourceProvider.getString(R.string.text_check_signal_quality),
            data = persistentListOf(0, 0, 0)
        )
    }

}