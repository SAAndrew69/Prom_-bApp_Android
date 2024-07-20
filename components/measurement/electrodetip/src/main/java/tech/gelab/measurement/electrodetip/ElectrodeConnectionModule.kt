package tech.gelab.measurement.electrodetip

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.measurement.api.ElectrodeConnectionFeatureEntry
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ElectrodeConnectionModule {

    @Binds
    @Singleton
    fun bindElectrodeConnectionEntry(electrodeConnectionFeatureEntryImpl: ElectrodeConnectionFeatureEntryImpl): ElectrodeConnectionFeatureEntry

    @Binds
    fun bindElectrodeConnectionFeatureEventHandler(electrodeConnectionFeatureEntryImpl: ElectrodeConnectionFeatureEntryImpl): FeatureEventHandler<CloseDialogFeatureEvent>

    @Binds
    @IntoSet
    fun bindECComposableFeatureEntry(electrodeConnectionFeatureEntry: ElectrodeConnectionFeatureEntry): ComposableFeatureEntry

}