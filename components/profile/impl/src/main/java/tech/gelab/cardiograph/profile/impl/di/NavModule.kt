package tech.gelab.cardiograph.profile.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.profile.api.ProfileFeatureEntry
import tech.gelab.cardiograph.profile.impl.ProfileFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindProfileFeatureEntry(profileFeatureEntryImpl: ProfileFeatureEntryImpl): ProfileFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(profileFeatureEntry: ProfileFeatureEntry): ComposableFeatureEntry

}