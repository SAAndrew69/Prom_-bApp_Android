package tech.gelab.cardiograph.authorization.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.authorization.api.AuthFeatureEntry
import tech.gelab.cardiograph.authorization.impl.AuthFeatureEntryImpl
import tech.gelab.cardiograph.authorization.impl.AuthFeatureEvent
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler

@Module
@InstallIn(SingletonComponent::class)
interface AuthorizationNavModule {

    @Binds
    fun bindAuthFeatureEntry(authFeatureEntryImpl: AuthFeatureEntryImpl): AuthFeatureEntry

    @Binds
    fun provideAuthFeatureEntryEventHandler(authFeatureEntryImpl: AuthFeatureEntryImpl): FeatureEventHandler<AuthFeatureEvent>

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(authFeatureEntry: AuthFeatureEntry): ComposableFeatureEntry

}