package tech.gelab.cardiograph.bridge.impl.connection

import dagger.Subcomponent
import tech.gelab.cardiograph.bridge.api.CardioBleScanner

@Subcomponent(modules = [ConnectionModule::class])
interface ConnectionComponent {

    val scanner: CardioBleScanner

    val cardioBleManager: CardioBleManager

    @Subcomponent.Factory
    interface Factory {
        fun create(): ConnectionComponent
    }

}