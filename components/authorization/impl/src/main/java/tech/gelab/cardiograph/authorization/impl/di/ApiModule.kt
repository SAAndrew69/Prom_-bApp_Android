package tech.gelab.cardiograph.authorization.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.authorization.api.AuthService
import tech.gelab.cardiograph.authorization.impl.data.AuthServiceImpl

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    @Binds
    fun bindAuthService(authServiceImpl: AuthServiceImpl): AuthService

}