package com.snapd.domain.providers

import com.snapd.domain.models.TrackState
import kotlinx.coroutines.flow.MutableStateFlow

interface GaidProvider {
    suspend fun provideGaid(liveTrackState: MutableStateFlow<TrackState>)

}