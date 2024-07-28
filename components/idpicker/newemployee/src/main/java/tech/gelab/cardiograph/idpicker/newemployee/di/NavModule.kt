package tech.gelab.cardiograph.idpicker.newemployee.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.gelab.cardiograph.core.ui.navigation.AggregateFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.ComposableFeatureEntry
import tech.gelab.cardiograph.core.ui.navigation.FeatureEventHandler
import tech.gelab.cardiograph.idpicker.api.NewEmployeeFeatureEntry
import tech.gelab.cardiograph.idpicker.newemployee.NewEmployeeFeatureEntryImpl
import tech.gelab.cardiograph.idpicker.newemployee.NewEmployeeFeatureEvent

@Module
@InstallIn(SingletonComponent::class)
interface NavModule {

    @Binds
    fun bindFeatureEventHandler(newEmployeeFeatureEntryImpl: NewEmployeeFeatureEntryImpl): FeatureEventHandler<NewEmployeeFeatureEvent>

    @Binds
    fun bindNewEmployeeFeatureEntry(newEmployeeFeatureEntryImpl: NewEmployeeFeatureEntryImpl): NewEmployeeFeatureEntry

    @Binds
    fun bindAggregateFeatureEntry(newEmployeeFeatureEntry: NewEmployeeFeatureEntry): AggregateFeatureEntry

}