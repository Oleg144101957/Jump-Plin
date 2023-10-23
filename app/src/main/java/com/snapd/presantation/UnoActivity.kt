package com.snapd.presantation

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.snapd.databinding.ActivityUnoBinding
import com.snapd.domain.features.CollectTrackDataUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class UnoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnoBinding

    private val collectTrackDataUseCase by inject<CollectTrackDataUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        collectTrackDataUseCase.getDataFromAllSources()

        goToTheNextMenuActivity()

    }

    private fun goToTheNextMenuActivity() {
        val intentToTheMenu = Intent(this, DosActivity::class.java)
        lifecycleScope.launch {
            delay(2000)
            startActivity(intentToTheMenu)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}