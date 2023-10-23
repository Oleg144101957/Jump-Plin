package com.snapd.domain.features

import android.util.Log
import com.snapd.domain.providers.AdbProvider

class CollectTrackDataUseCase(private val adbProvider: AdbProvider) {

    fun getDataFromAllSources(){
        val adbData = adbProvider.provideAdb()
        Log.d("123123", "Data is $adbData")

    }

}