package com.snapd.presantation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.snapd.data.providers.NO_DATA
import com.snapd.data.storage.EMPTY_DESTINATION
import com.snapd.data.storage.HARM_DESTINATION
import com.snapd.data.storage.SharedStorageImpl
import com.snapd.databinding.ActivityTresBinding
import com.snapd.domain.models.PlayerDocs
import com.snapd.domain.storage.AppStorage
import com.snapd.presantation.customUI.PolicyScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTresBinding
    private lateinit var applicationStorage: AppStorage
    private lateinit var policyScreen: PolicyScreen

    private val liveStatus = MutableStateFlow(NO_DATA)

    private var chooseCallback: ValueCallback<Array<Uri>>? = null
    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            chooseCallback?.onReceiveValue(it.toTypedArray())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applicationStorage = SharedStorageImpl(this)

        lifecycleScope.launch {
            liveStatus.collect{
                if (it == HARM_DESTINATION){
                    navigateToTheDosScreen()
                }
            }
        }

        policyScreen = PolicyScreen(this, liveStatus = liveStatus)
        policyScreen.initPolicyScreen(playerDocs = object : PlayerDocs{
            override fun pickPhotos(selectedDocs: ValueCallback<Array<Uri>>?) {
                chooseCallback = selectedDocs
            }
        }, activityLauncher = activityLauncher)

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        policyScreen.saveState(bundle)
        outState.putBundle("a", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        policyScreen.restoreState(savedInstanceState)
    }

    private fun navigateToTheDosScreen(){
        val intentToTheDosScreen = Intent(this, DosActivity::class.java)
        startActivity(intentToTheDosScreen)
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