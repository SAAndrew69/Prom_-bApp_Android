package tech.gelab.cardiograph.pairing.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.pairing.api.PairingFeatureEntry
import tech.gelab.cardiograph.pairing.impl.PairingFeatureEntryImpl
import tech.gelab.cardiograph.pairing.impl.PairingFeatureEvent

@Module
@InstallIn(SingletonComponent::class)
interface PairingNavModule {

    @Binds
    fun bindPairingFeatureEntry(pairingFeatureEntryImpl: PairingFeatureEntryImpl): PairingFeatureEntry

    @Binds
    @IntoSet
    fun bindAggregateFeatureEntry(pairingFeatureEntry: PairingFeatureEntry): AggregateFeatureEntry

}