package com.openappslabs.fiveg.services

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.openappslabs.fiveg.utils.TileRadioInfo

class FiveGTileService : TileService() {
    override fun onStartListening() {
        super.onStartListening()
        val tile = qsTile ?: return
        val componentName = TileRadioInfo.preResolve(this)

        tile.state = when (componentName) {
            null -> Tile.STATE_UNAVAILABLE
            else -> Tile.STATE_INACTIVE
        }
        tile.updateTile()
    }

    override fun onClick() {
        unlockAndRun {
            TileRadioInfo.openRadioInfo(this)
        }
    }
}