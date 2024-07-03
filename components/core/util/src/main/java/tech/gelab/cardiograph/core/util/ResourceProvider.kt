package tech.gelab.cardiograph.core.util

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

}