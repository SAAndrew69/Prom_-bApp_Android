package tech.gelab.cardiograph.idpicker.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.idpicker.api.IdentifierPickerFeatureEntry
import tech.gelab.cardiograph.idpicker.impl.IdentifierPickerFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindIdentifierPickerFeatureEntry(identifierPickerFeatureEntryImpl: IdentifierPickerFeatureEntryImpl) : IdentifierPickerFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(identifierPickerFeatureEntry: IdentifierPickerFeatureEntry) : ComposableFeatureEntry

}