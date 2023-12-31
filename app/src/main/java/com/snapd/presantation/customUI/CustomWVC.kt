package com.snapd.presantation.customUI

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.snapd.data.storage.HARM_DESTINATION
import com.snapd.domain.features.DestinationChecker
import com.snapd.domain.storage.AppStorage
import kotlinx.coroutines.flow.MutableStateFlow

class CustomWVC(
    private val liveStatus: MutableStateFlow<String>,
    private val applicationStorage: AppStorage
) : WebViewClient(){

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString() ?: return super.shouldOverrideUrlLoading(view, request)
        if (!url.startsWith("http")) {
            return try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                view?.context?.startActivity(intent)
                true
            } catch (e: ActivityNotFoundException) {
                view?.let {
                    Toast.makeText(
                        it.context,
                        "${
                            url.substringBefore("://").uppercase()
                        } supported applications not found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        CookieManager.getInstance().flush()

        Log.d("123123", "THE URL IS $url")

        if (url != null){
            val destinationChecker = DestinationChecker(url, applicationStorage)
            val checkResult = destinationChecker.checkDestination()

            if (checkResult == HARM_DESTINATION){
                liveStatus.value = HARM_DESTINATION
                applicationStorage.saveDestination(HARM_DESTINATION)
            }
        }
    }
}