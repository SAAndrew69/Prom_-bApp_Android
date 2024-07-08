package tech.gelab.cardiograph.authorization.skip

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.authorization.api.SkipAuthFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindSkipAuthFeatureEntry(skipAuthFeatureEntryImpl: SkipAuthFeatureEntryImpl) : SkipAuthFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(skipAuthFeatureEntry: SkipAuthFeatureEntry) : ComposableFeatureEntry

}