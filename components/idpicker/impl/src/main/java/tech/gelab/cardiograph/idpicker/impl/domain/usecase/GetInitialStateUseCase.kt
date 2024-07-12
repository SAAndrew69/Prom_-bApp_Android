package tech.gelab.cardiograph.idpicker.impl.domain.usecase

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tech.gelab.cardiograph.idpicker.impl.domain.PickerState
import tech.gelab.cardiograph.storage.pb.DeviceSettings
import javax.inject.Inject

class GetInitialStateUseCase @Inject constructor(private val deviceSettingsDataStore: DataStore<DeviceSettings>) {
    fun invoke(): PickerState {
        val isConnected = runBlocking { deviceSettingsDataStore.data.first().deviceConnectionPassed }
        return PickerState(isDeviceConnected = isConnected)
    }
}