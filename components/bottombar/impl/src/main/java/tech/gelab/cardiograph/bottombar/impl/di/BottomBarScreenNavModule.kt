package tech.gelab.cardiograph.bottombar.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.bottombar.api.BottomNavigationFeatureEntry
import tech.gelab.cardiograph.bottombar.impl.BottomNavigationFeatureEntryImpl
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry

@Module
@InstallIn(SingletonComponent::class)
interface BottomBarScreenNavModule {

    @Binds
    fun bindBottomBarFeatureEntry(bottomBarFeatureEntryImpl: BottomNavigationFeatureEntryImpl): BottomNavigationFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(bottomNavigationFeatureEntry: BottomNavigationFeatureEntry): ComposableFeatureEntry

}