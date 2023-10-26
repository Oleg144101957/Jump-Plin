package com.snapd.presantation.customUI

import android.content.Context
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import com.snapd.data.storage.SharedStorageImpl
import com.snapd.domain.models.PlayerDocs
import com.snapd.domain.storage.AppStorage
import kotlinx.coroutines.flow.MutableStateFlow

class PolicyScreen(context: Context, private val liveStatus: MutableStateFlow<String>) : WebView(context) {

    private val applicationStorage: AppStorage = SharedStorageImpl(context)

    fun initPolicyScreen(activityLauncher: ActivityResultLauncher<String>, playerDocs: PlayerDocs){
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.userAgentString = settings.userAgentString.setAgent()

        webChromeClient = CustomWCC(playerDocs = playerDocs, launcher = activityLauncher)
        webViewClient = CustomWVC(liveStatus = liveStatus, applicationStorage = applicationStorage)
    }

    private fun String.setAgent(): String{
        return this.replace("; wv", "")
    }
}