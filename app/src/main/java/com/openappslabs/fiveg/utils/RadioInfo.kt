package com.openappslabs.fiveg.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast

object RadioInfo {
    private const val SETTINGS_PACKAGE = "com.android.settings"
    private const val SETTINGS_CLASS = "com.android.settings.RadioInfo"

    private const val PHONE_PACKAGE = "com.android.phone"
    private const val PHONE_CLASS = "com.android.phone.settings.RadioInfo"

    fun openRadioInfo(context: Context) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val success = tryStartActivity(context, intent, SETTINGS_PACKAGE, SETTINGS_CLASS) || tryStartActivity(context, intent, PHONE_PACKAGE, PHONE_CLASS)
        if (!success) {
            Toast.makeText(context, "Could not open Radio Info settings", Toast.LENGTH_SHORT).show()
        }
    }

    private fun tryStartActivity(
        context: Context,
        intent: Intent,
        pkg: String,
        cls: String
    ): Boolean {
        return try {
            intent.component = ComponentName(pkg, cls)
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }
}