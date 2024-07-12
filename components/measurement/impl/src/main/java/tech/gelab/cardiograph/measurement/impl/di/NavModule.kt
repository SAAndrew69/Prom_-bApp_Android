package tech.gelab.cardiograph.measurement.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.BottomSheetFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.measurement.api.MeasurementFeatureEntry
import tech.gelab.cardiograph.measurement.impl.MeasurementFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindMeasurementFeatureEntry(measurementFeatureEntryImpl: MeasurementFeatureEntryImpl): MeasurementFeatureEntry

    @Binds
    @IntoSet
    fun bindBottomSheetFeatureEntry(measurementFeatureEntry: MeasurementFeatureEntry): BottomSheetFeatureEntry

}