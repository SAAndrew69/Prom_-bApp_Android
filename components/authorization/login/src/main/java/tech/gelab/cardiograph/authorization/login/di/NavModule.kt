package tech.gelab.cardiograph.authorization.login.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.login.LoginFeatureEntryImpl
import tech.gelab.cardiograph.authorization.login.LoginFeatureEvent
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindLoginFeatureEntry(loginFeatureEntryImpl: LoginFeatureEntryImpl): LoginFeatureEntry

    @Binds
    fun bindLoginFeatureEventHandler(loginFeatureEntryImpl: LoginFeatureEntryImpl): FeatureEventHandler<LoginFeatureEvent>

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(loginFeatureEntry: LoginFeatureEntry): ComposableFeatureEntry

}