package com.snapd.presantation.customUI

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import com.snapd.domain.models.PlayerDocs

class CustomWCC(
    private val playerDocs: PlayerDocs,
    private val launcher: ActivityResultLauncher<String>
) : WebChromeClient() {


    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {

        val contentToShow = "image/*"
        playerDocs.pickPhotos(filePathCallback)
        launcher.launch(contentToShow)

        return true
    }




}