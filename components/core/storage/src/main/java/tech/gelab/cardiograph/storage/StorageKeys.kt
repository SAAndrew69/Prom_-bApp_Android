package tech.gelab.cardiograph.storage

import androidx.datastore.preferences.core.stringPreferencesKey

object StorageKeys {

    val MAC = stringPreferencesKey("mac")
    val NAME = stringPreferencesKey("name")

}