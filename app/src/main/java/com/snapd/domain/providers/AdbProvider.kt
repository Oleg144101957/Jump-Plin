package com.snapd.domain.providers

import com.snapd.domain.models.TrackState
import kotlinx.coroutines.flow.MutableStateFlow

interface AdbProvider {
    fun provideAdb(liveTrackState: MutableStateFlow<TrackState>)

}