package com.snapd.presantation

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.snapd.databinding.ActivityJumpBinding

class JumpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJumpBinding
    private var xOffset = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJumpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val screenHeight = resources.displayMetrics.heightPixels.toFloat()
        val screenWidth = resources.displayMetrics.widthPixels.toFloat()
        val halfOfTheScreenWidth = screenWidth/2F


        val jumpAnim = TranslateAnimation(0F, 0F, 0F, -screenHeight + binding.jumpingBall.height)
        jumpAnim.duration = 1000
        jumpAnim.fillAfter = true
        jumpAnim.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) { }

            override fun onAnimationEnd(animation: Animation?) {
                binding.jumpingBall.translationX = xOffset
                binding.jumpingBall.startAnimation(jumpAnim)
            }

            override fun onAnimationRepeat(animation: Animation?) { }
        })

        binding.jumpingBall.startAnimation(jumpAnim)


        binding.btnRight.setOnClickListener {
            val newOffset = xOffset + halfOfTheScreenWidth/5
            if (newOffset in -halfOfTheScreenWidth..halfOfTheScreenWidth){
                xOffset = newOffset
                binding.platform.translationX = xOffset
            }
        }

        binding.btnLeft.setOnClickListener {
            val newOffset = xOffset - halfOfTheScreenWidth/5

            Log.d("123123", "New offset is $newOffset screen width / 2 is $halfOfTheScreenWidth")

            if (newOffset in -halfOfTheScreenWidth..halfOfTheScreenWidth){
                xOffset = newOffset
                binding.platform.translationX = xOffset
            }

        }

    }
}