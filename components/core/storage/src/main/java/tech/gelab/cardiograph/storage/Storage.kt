package tech.gelab.cardiograph.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class Storage(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "device")
    private val dataStore = context.dataStore.data

    suspend fun <T> getStringValue(key: Preferences.Key<T>) : T? {
        return dataStore.first()[key]
    }

    suspend fun writeValue() {

    }

}