package tech.gelab.cardiograph.core.notification

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

class ToastHelper(private val context: Context) {

    fun showToast(text: String, length: Int) {
        Toast.makeText(context, text, length).show()
    }

    fun showToast(@StringRes resId: Int, length: Int) {
        Toast.makeText(context, resId, length).show()
    }

}