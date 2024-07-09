package tech.gelab.cardiograph.ui.dialog.dialognavprovider

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindTextDialogFeatureEntry(textDialogFeatureEntryImpl: TextDialogFeatureEntryImpl): TextDialogFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(textDialogFeatureEntry: TextDialogFeatureEntry): ComposableFeatureEntry

}