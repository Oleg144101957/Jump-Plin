package com.snapd.domain.providers

import android.os.Bundle
import android.webkit.CookieManager
import androidx.appcompat.app.AppCompatActivity
import com.snapd.databinding.ActivityTresBinding

class TresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTresBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //insert web view
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(, true)
    }
}