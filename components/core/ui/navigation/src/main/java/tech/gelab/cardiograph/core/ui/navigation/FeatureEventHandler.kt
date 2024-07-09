package tech.gelab.cardiograph.core.ui.navigation

interface FeatureEventHandler<FeatureEvent> {

    fun obtainEvent(event: FeatureEvent)

}