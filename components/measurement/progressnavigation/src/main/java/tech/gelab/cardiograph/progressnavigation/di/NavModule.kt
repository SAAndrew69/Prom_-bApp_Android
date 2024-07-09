package tech.gelab.cardiograph.progressnavigation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import tech.gelab.cardiograph.measurement.api.ProgressNavigationFeatureEntry
import tech.gelab.cardiograph.measurement.api.SignalQualityFeatureEntry
import tech.gelab.cardiograph.progressnavigation.ElectrodeConnectionFeatureEntryImpl
import tech.gelab.cardiograph.progressnavigation.ProgressNavigationFeatureEntryFeatureEntryImpl
import tech.gelab.cardiograph.progressnavigation.SignalQualityFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindProgressNavigationEntry(progressNavigationFeatureEntryImpl: ProgressNavigationFeatureEntryFeatureEntryImpl): ProgressNavigationFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(progressNavigationFeatureEntry: ProgressNavigationFeatureEntry): ComposableFeatureEntry

    @Binds
    fun bindElectrodeConnectionEntry(electrodeConnectionFeatureEntryImpl: ElectrodeConnectionFeatureEntryImpl): ElectrodeConnectionFeatureEntry

    @Binds
    @IntoSet
    fun bindECComposableFeatureEntry(electrodeConnectionFeatureEntry: ElectrodeConnectionFeatureEntry): ComposableFeatureEntry

    @Binds
    fun bindSignalQualityFeatureEntry(signalQualityFeatureEntryImpl: SignalQualityFeatureEntryImpl): SignalQualityFeatureEntry

    @Binds
    @IntoSet
    fun bindSQComposableFeatureEntry(signalQualityFeatureEntry: SignalQualityFeatureEntry): ComposableFeatureEntry

}