package com.snapd.presantation.customUI

import android.content.Context
import android.webkit.CookieManager
import android.webkit.WebView

class PolicyScreen(context: Context) : WebView(context) {


    fun initPolicyScreen(){
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.userAgentString = settings.userAgentString.setA()

        webChromeClient = CustomWCC()
        webViewClient = CustomWVC()
    }



    private fun String.setA(): String{
        return this.replace(": wv", "")
    }
}