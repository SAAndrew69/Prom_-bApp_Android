package tech.gelab.cardiograph.measurement.impl

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tech.gelab.cardiograph.measurement.api.MeasurementApi
import tech.gelab.cardiograph.storage.pb.Settings
import javax.inject.Inject
import javax.inject.Singleton

class MeasurementApiImpl @Inject constructor(private val settingsDataStore: DataStore<Settings>): MeasurementApi {
    override fun shouldShowElectrodeConnectionTip(): Boolean {
        return runBlocking {
            settingsDataStore.data.first().showElectrodeTip
        }
    }
}