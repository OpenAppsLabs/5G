package com.openappslabs.fiveg.utils

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.service.quicksettings.TileService
import android.widget.Toast

object TileRadioInfo {
    private val COMPONENTS = arrayOf(
        Pair("com.android.settings", "com.android.settings.RadioInfo"),
        Pair("com.android.settings", "com.android.settings.Settings\$RadioInfoActivity"),
        Pair("com.android.settings", "com.android.settings.TestingSettings"),
        Pair("com.android.settings", "com.android.settings.Settings\$TestingSettingsActivity"),
        Pair("com.android.phone", "com.android.phone.settings.RadioInfo"),
        Pair("com.android.phone", "com.android.phone.RadioInfo"),
        Pair("com.qualcomm.qti.networksetting", "com.qualcomm.qti.networksetting.MobileNetworkSettings"),
        Pair("com.mediatek.engineermode", "com.mediatek.engineermode.EngineerMode"),
        Pair("com.mediatek.engineermode", "com.mediatek.engineermode.modemtest.ModemTestActivity"),
        Pair("com.sec.android.app.servicemodeapp", "com.sec.android.app.servicemodeapp.ServiceModeApp"),
        Pair("com.android.phone", "com.android.phone.MobileNetworkSettings"),
        Pair("com.android.settings", "com.android.settings.BandMode")
    )

    private var cachedComponent: ComponentName? = null
    fun preResolve(context: Context): ComponentName? {
        if (cachedComponent != null) return cachedComponent

        for (comp in COMPONENTS) {
            val pkg = comp.first
            val cls = comp.second
            val componentName = ComponentName(pkg, cls)
            if (isActivityExists(context, componentName)) {
                cachedComponent = componentName
                return componentName
            }
        }
        return null
    }

    fun openRadioInfo(context: Context) {
        val component = cachedComponent ?: preResolve(context)

        if (component == null) {
            Toast.makeText(context, "Radio Info not supported on this device", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent().apply {
            this.component = component
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        if (context is TileService) {
            startActivityFromTile(context, intent)
        } else {
            tryStartActivity(context, intent)
        }
    }

    private fun isActivityExists(context: Context, componentName: ComponentName): Boolean {
        return try {
            val info = context.packageManager.getActivityInfo(componentName, 0)
            info.exported
        } catch (e: Exception) {
            false
        }
    }

    private fun tryStartActivity(context: Context, intent: Intent): Boolean {
        return try {
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun startActivityFromTile(tileService: TileService, intent: Intent) {
        try {
            val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            val pendingIntent = PendingIntent.getActivity(
                tileService,
                0,
                intent,
                flags
            )
            tileService.startActivityAndCollapse(pendingIntent)
        } catch (e: Exception) {
            cachedComponent = null
        }
    }
}