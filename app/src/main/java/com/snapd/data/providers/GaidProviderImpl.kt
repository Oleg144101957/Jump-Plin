package com.snapd.data.providers

import android.content.Context
import android.util.Log
import com.facebook.internal.AttributionIdentifiers
import com.snapd.domain.models.TrackState
import com.snapd.domain.providers.GaidProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext


const val NO_DATA = "NO_DATA"
class GaidProviderImpl(private val context: Context) : GaidProvider {
    override suspend fun provideGaid(liveTrackState: MutableStateFlow<TrackState>) = withContext(Dispatchers.IO){
        val gaid = AttributionIdentifiers.getAttributionIdentifiers(context)?.androidAdvertiserId ?: NO_DATA
        liveTrackState.value = liveTrackState.value.copy(gaid = gaid)
    }
}