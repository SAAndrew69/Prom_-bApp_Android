package tech.gelab.cardiograph.measurement.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.cardiograph.measurement.impl.MeasurementApiImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    @Binds
    @Singleton
    fun bindMeasurementApi(measurementApiImpl: MeasurementApiImpl): MeasurementApi

}