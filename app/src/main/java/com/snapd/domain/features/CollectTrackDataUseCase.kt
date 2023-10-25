package com.snapd.domain.features

import com.snapd.domain.models.TrackState
import com.snapd.domain.providers.AdbProvider
import com.snapd.domain.providers.GaidProvider
import com.snapd.domain.providers.RefProvider
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject
import org.koin.android.ext.android.inject

class CollectTrackDataUseCase() {
    suspend fun getDataFromAllSources(liveTrackState: MutableStateFlow<TrackState>){

//        val adbState = adbProvider.provideAdb()
//        liveTrackState.value = liveTrackState.value.copy(adbState = adbState)
//
//        val refData = refProvider.provideRef()
//        liveTrackState.value = liveTrackState.value.copy(ref = refData)
//
//        val gaid = gaidProvider.provideGaid()
//        liveTrackState.value = liveTrackState.value.copy(gaid = gaid)
    }
}