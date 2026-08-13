package com.openappslabs.fiveg.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.telephony.SubscriptionManager
import android.widget.Toast
import com.openappslabs.fiveg.R

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
            Toast.makeText(context, context.getString(R.string.error_open_radio_info), Toast.LENGTH_SHORT).show()
        }
    }

    fun openNetworkSettings(context: Context) {
        try {
            val subId = SubscriptionManager.getDefaultDataSubscriptionId()
            val intent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (subId != SubscriptionManager.INVALID_SUBSCRIPTION_ID) {
                    putExtra("android.provider.extra.SUB_ID", subId)
                }
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            try {
                val fallbackIntent = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(fallbackIntent)
            } catch (_: Exception) {
                Toast.makeText(context, context.getString(R.string.error_open_network_settings), Toast.LENGTH_SHORT).show()
            }
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