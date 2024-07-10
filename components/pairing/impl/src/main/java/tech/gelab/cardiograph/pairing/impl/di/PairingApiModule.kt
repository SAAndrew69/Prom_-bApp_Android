package tech.gelab.cardiograph.pairing.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.pairing.api.PairingApi
import tech.gelab.cardiograph.pairing.impl.PairingApiImpl

@Module
@InstallIn(SingletonComponent::class)
interface PairingApiModule {

    @Binds
    fun bindPairingApi(pairingApiImpl: PairingApiImpl): PairingApi

}