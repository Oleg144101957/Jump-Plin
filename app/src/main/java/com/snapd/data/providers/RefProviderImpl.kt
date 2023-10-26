package com.snapd.data.providers

import android.content.Context
import android.os.DeadObjectException
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.snapd.domain.models.TrackState
import com.snapd.domain.providers.RefProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class RefProviderImpl(private val context: Context) : RefProvider {
    override suspend fun provideRef(liveTrackState: MutableStateFlow<TrackState>) = withContext(
        Dispatchers.IO){

        val rClient = InstallReferrerClient.newBuilder(context).build()

        rClient.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(respondCode: Int) {

                if (respondCode == InstallReferrerClient.InstallReferrerResponse.OK) {

                    try {
                        val result = rClient?.installReferrer?.installReferrer
                            ?: NO_DATA
                        rClient.endConnection()
                        liveTrackState.value = liveTrackState.value.copy(ref = result)
                    } catch (e: DeadObjectException) {
                        liveTrackState.value = liveTrackState.value.copy(ref = NO_DATA)
                    }

                } else {

                    liveTrackState.value = liveTrackState.value.copy(ref = NO_DATA)
                }
            }

            override fun onInstallReferrerServiceDisconnected() {

            }
        })
    }
}