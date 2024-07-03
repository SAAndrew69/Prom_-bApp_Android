package tech.gelab.cardiograph.scanner.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import tech.gelab.cardiograph.core.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.scanner.api.ScannerFeatureEntry
import tech.gelab.cardiograph.scanner.impl.ScannerFeatureEntryImpl

@Module
@InstallIn(SingletonComponent::class)
interface ScannerNavModule {

    @Binds
    fun bindScannerFeatureEntry(scannerFeatureEntryImpl: ScannerFeatureEntryImpl): ScannerFeatureEntry

    @Binds
    @IntoSet
    fun bindComposableFeatureEntry(scannerFeatureEntry: ScannerFeatureEntry): ComposableFeatureEntry

}