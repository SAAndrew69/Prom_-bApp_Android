package tech.gelab.cardiograph.bridge.impl.scanner

import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.bridge.api.CardioBleScanner

@Module
@InstallIn(SingletonComponent::class)
interface ScannerModule {

    @Binds
    @Reusable
    fun bindCardioBleScanner(cardioBleScannerImpl: CardioBleScannerImpl): CardioBleScanner

}