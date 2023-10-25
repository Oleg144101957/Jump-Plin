package com.snapd.data.providers

import android.content.Context
import android.util.Log
import com.facebook.internal.AttributionIdentifiers
import com.snapd.domain.models.TrackState
import com.snapd.domain.providers.GaidProvider
import kotlinx.coroutines.flow.MutableStateFlow


const val NO_DATA = "NO_DATA"
class GaidProviderImpl(private val context: Context) : GaidProvider {
    override suspend fun provideGaid(liveTrackState: MutableStateFlow<TrackState>){
        Log.d("123123", "provideGaid method")
        //val gaid = AttributionIdentifiers.getAttributionIdentifiers(context)?.androidAdvertiserId
        //Log.d("123123", "provideGaid method GAID is $gaid")
        liveTrackState.value = liveTrackState.value.copy(gaid = "here will be gadid soon")
    }
}