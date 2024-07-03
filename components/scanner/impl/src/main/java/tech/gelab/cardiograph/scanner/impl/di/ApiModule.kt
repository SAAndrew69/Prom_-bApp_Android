package tech.gelab.cardiograph.scanner.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.scanner.api.ScannerApi
import tech.gelab.cardiograph.scanner.impl.data.ScannerApiImpl

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    @Binds
    fun bindScannerApi(scannerApiImpl: ScannerApiImpl): ScannerApi

}