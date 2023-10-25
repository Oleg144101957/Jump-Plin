package com.snapd.presantation.customUI

import android.content.Context
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import com.snapd.data.storage.SharedStorageImpl
import com.snapd.domain.storage.AppStorage

class PolicyScreen(context: Context) : WebView(context) {

    private val applicationStorage: AppStorage = SharedStorageImpl(context)

    fun initPolicyScreen(getContent: ActivityResultLauncher<String>){
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.userAgentString = settings.userAgentString.setAgent()

        webChromeClient = CustomWCC()
        webViewClient = CustomWVC()
    }

    private fun String.setAgent(): String{
        return this.replace(": wv", "")
    }
}