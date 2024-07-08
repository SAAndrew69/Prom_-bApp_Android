package tech.gelab.cardiograph.reports.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.reports.api.ReportsFeatureEntry
import tech.gelab.cardiograph.reports.impl.ReportsFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindReportsFeatureEntry(reportsFeatureEntryImpl: ReportsFeatureEntryImpl): ReportsFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(reportsFeatureEntry: ReportsFeatureEntry): ComposableFeatureEntry

}