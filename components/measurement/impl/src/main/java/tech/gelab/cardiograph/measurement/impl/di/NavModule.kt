package tech.gelab.cardiograph.measurement.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.BottomSheetFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import tech.gelab.cardiograph.measurement.impl.MeasurementFeatureEntryImpl
import tech.gelab.cardiograph.measurement.impl.MeasurementFeatureEvent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    @Singleton
    fun bindMeasurementFeatureEntry(measurementFeatureEntryImpl: MeasurementFeatureEntryImpl): MeasurementFeatureEntry

    @Binds
    fun bindMeasurementFeatureEvent(measurementFeatureEntryImpl: MeasurementFeatureEntryImpl): FeatureEventHandler<MeasurementFeatureEvent>

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(measurementFeatureEntry: MeasurementFeatureEntry): ComposableFeatureEntry

}