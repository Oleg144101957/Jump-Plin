package com.snapd.data.providers

import android.content.Context
import android.provider.Settings
import com.snapd.domain.models.TrackState
import com.snapd.domain.providers.AdbProvider
import kotlinx.coroutines.flow.MutableStateFlow

class AdbProviderImpl(private val context: Context) : AdbProvider {
    override fun provideAdb(liveTrackState: MutableStateFlow<TrackState>){
        val adb = Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
        liveTrackState.value = liveTrackState.value.copy(adbState = adb)
    }
}