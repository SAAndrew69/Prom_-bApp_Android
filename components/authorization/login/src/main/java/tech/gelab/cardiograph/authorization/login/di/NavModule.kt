package tech.gelab.cardiograph.authorization.login.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.authorization.api.LoginFeatureEntry
import tech.gelab.cardiograph.authorization.login.LoginFeatureEntryImpl
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindLoginFeatureEntry(loginFeatureEntryImpl: LoginFeatureEntryImpl): LoginFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(loginFeatureEntry: LoginFeatureEntry): ComposableFeatureEntry

}