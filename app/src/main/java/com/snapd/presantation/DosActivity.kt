package com.snapd.presantation

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.snapd.databinding.ActivityDosBinding

class DosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListenners()

    }

    private fun setOnClickListenners() {

        binding.btnPlay.setOnClickListener {
            goToTheJumpActivity()
        }

        binding.btnCloseApp.setOnClickListener {
            finishAffinity()
        }
    }

    private fun goToTheJumpActivity(){
        val intentToTheJumpActivity = Intent(this, JumpActivity::class.java)
        startActivity(intentToTheJumpActivity)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}