package com.snapd.presantation

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.snapd.databinding.ActivityJumpBinding

class JumpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJumpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJumpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        val jumpAnim = TranslateAnimation(0F, 0F, 0F, -screenHeight + binding.jumpingBall.height)
        jumpAnim.duration = 1000
        jumpAnim.fillAfter = true
        jumpAnim.setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) { }

            override fun onAnimationEnd(animation: Animation?) {
                binding.jumpingBall.startAnimation(jumpAnim)
            }

            override fun onAnimationRepeat(animation: Animation?) { }
        })

        binding.jumpingBall.startAnimation(jumpAnim)


    }
}