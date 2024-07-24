package tech.gelab.cardiograph.conclusion.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.conclusion.api.ConclusionFeatureEntry
import tech.gelab.cardiograph.conclusion.impl.ConclusionFeatureEntryImpl
import tech.gelab.cardiograph.conclusion.impl.ConclusionFeatureEvent
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindConclusionFeatureEntry(conclusionFeatureEntryImpl: ConclusionFeatureEntryImpl): ConclusionFeatureEntry

    @Binds
    fun bindComposableFeatureEntry(conclusionFeatureEntry: ConclusionFeatureEntry): ComposableFeatureEntry

    @Binds
    fun bindFeatureEventHandler(conclusionFeatureEntryImpl: ConclusionFeatureEntryImpl): FeatureEventHandler<ConclusionFeatureEvent>

}