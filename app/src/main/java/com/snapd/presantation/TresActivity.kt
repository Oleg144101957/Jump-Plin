package com.snapd.presantation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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



        if (applicationStorage.getDestination() == EMPTY_DESTINATION){
            val gaid = intent.getStringExtra(GAID)
            val adb = intent.getStringExtra(ADB)
            val ref = intent.getStringExtra(REFERRER)
            Log.d("123123", "gaid is $gaid, adb is $adb, ref is $ref")
        }

        //insert web view
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(policyScreen, true)

        binding.root.addView(policyScreen)

        policyScreen.loadUrl("https://google.com")





    }
}