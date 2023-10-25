package com.snapd.data.providers

import android.content.Context
import com.snapd.domain.models.TrackState
import com.snapd.domain.providers.RefProvider
import kotlinx.coroutines.flow.MutableStateFlow

class RefProviderImpl(private val context: Context) : RefProvider {

    override suspend fun provideRef(liveTrackState: MutableStateFlow<TrackState>){
        liveTrackState.value = liveTrackState.value.copy(ref = "Hello world")
    }
}