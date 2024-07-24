package tech.gelab.measurement.electrodetip.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import tech.gelab.measurement.electrodetip.CloseDialogFeatureEvent
import tech.gelab.measurement.electrodetip.ElectrodeConnectionFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface ElectrodeConnectionModule {

    @Binds
    fun bindElectrodeConnectionEntry(electrodeConnectionFeatureEntryImpl: ElectrodeConnectionFeatureEntryImpl): ElectrodeConnectionFeatureEntry

    @Binds
    fun bindElectrodeConnectionFeatureEventHandler(electrodeConnectionFeatureEntryImpl: ElectrodeConnectionFeatureEntryImpl): FeatureEventHandler<CloseDialogFeatureEvent>

    @Binds
    @IntoSet
    fun bindECComposableFeatureEntry(electrodeConnectionFeatureEntry: ElectrodeConnectionFeatureEntry): ComposableFeatureEntry

}