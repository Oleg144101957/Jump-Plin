package com.snapd.presantation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.snapd.data.storage.EMPTY_DESTINATION
import com.snapd.data.storage.SharedStorageImpl
import com.snapd.databinding.ActivityUnoBinding
import com.snapd.domain.features.CollectTrackDataUseCase
import com.snapd.domain.models.EMPTY
import com.snapd.domain.models.TrackState
import com.snapd.domain.providers.AdbProvider
import com.snapd.domain.providers.GaidProvider
import com.snapd.domain.providers.RefProvider
import com.snapd.domain.storage.AppStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.scope

const val GAID = "GAID"
const val REFERRER = "REFERRER"
const val ADB = "ADB"

class UnoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnoBinding
    private lateinit var applicationStorage: AppStorage

    val adbProvider: AdbProvider by inject()
    val refProvider: RefProvider by inject()
    val gaidProvider: GaidProvider by inject()

    private val liveTrackState = MutableStateFlow(TrackState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applicationStorage = SharedStorageImpl(this)
        val currentDestinationFromStorage = applicationStorage.getDestination()

        if (currentDestinationFromStorage == EMPTY_DESTINATION){
            lifecycleScope.launch {
                liveTrackState.collect{
                    if (it.gaid != EMPTY && it.ref != EMPTY && it.adbState != EMPTY){
                        //navigate to WebView
                        val intentToThePolicy = Intent(this@UnoActivity, TresActivity::class.java)

                        intentToThePolicy.putExtra(GAID, it.gaid)
                        intentToThePolicy.putExtra(REFERRER, it.ref)
                        intentToThePolicy.putExtra(ADB, it.adbState)

                        startActivity(intentToThePolicy)
                    }
                }
            }

            lifecycleScope.launch {
                adbProvider.provideAdb(liveTrackState = liveTrackState)
            }

        } else if (currentDestinationFromStorage.startsWith("http")) {
            //not first time have the destination
            val intentToThePolicy = Intent(this@UnoActivity, TresActivity::class.java)
            startActivity(intentToThePolicy)
        } else {
            goToTheNextMenuActivity()
        }

    }


    override fun onStart() {
        super.onStart()

        if (applicationStorage.getDestination() == EMPTY_DESTINATION){
            lifecycleScope.launch {
                refProvider.provideRef(liveTrackState = liveTrackState)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        if (applicationStorage.getDestination() == EMPTY_DESTINATION){
            lifecycleScope.launch {
                gaidProvider.provideGaid(liveTrackState = liveTrackState)
            }
        }


    }

    private fun goToTheNextMenuActivity() {
        val intentToTheMenu = Intent(this, DosActivity::class.java)
        lifecycleScope.launch {
            delay(1000)
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