package tech.gelab.cardiograph.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CardiographDatabase {
        return Room.databaseBuilder(
            context,
            CardiographDatabase::class.java,
            CardiographDatabase.NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideEmployeeRepository(cardiographDatabase: CardiographDatabase): EmployeeRepository {
        return EmployeeRepository(cardiographDatabase.employeeDao())
    }

}