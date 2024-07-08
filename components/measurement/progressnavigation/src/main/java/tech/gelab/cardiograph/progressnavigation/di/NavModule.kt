package tech.gelab.cardiograph.progressnavigation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.measurement.api.ProgressNavigationFeatureEntry
import tech.gelab.cardiograph.progressnavigation.ProgressNavigationFeatureEntryFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindProgressNavigationEntry(progressNavigationFeatureEntryImpl: ProgressNavigationFeatureEntryFeatureEntryImpl): ProgressNavigationFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(progressNavigationFeatureEntry: ProgressNavigationFeatureEntry): ComposableFeatureEntry

}