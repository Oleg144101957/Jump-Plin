package com.snapd.domain.providers

import com.snapd.domain.models.TrackState
import kotlinx.coroutines.flow.MutableStateFlow

interface RefProvider {
    suspend fun provideRef(liveTrackState: MutableStateFlow<TrackState>)

}