package com.snapd.presantation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.snapd.R
import com.snapd.databinding.ActivityJumpBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlin.random.Random

class JumpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJumpBinding
    private var xOffset = 0F
    private val scores = MutableStateFlow(0)
    private val time = MutableStateFlow(20)
    private var screenWidth: Float = 0F
    private var halfOfTheScreenWidth: Float = 0F
    private var gameMode: GameMode = GameMode.PLAYING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJumpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenWidth = resources.displayMetrics.widthPixels.toFloat()
        halfOfTheScreenWidth = screenWidth / 2F

        setButtonsClickListenners()
        begingTimerCountDown()
        setObservers()

    }

    private fun setObservers() {
        lifecycleScope.launch {
            scores.collect{
                binding.points.text = "Your points: ${scores.value}"

                if (it%3 == 0 && it>2){
                    showCompliments()
                }
            }
        }

        lifecycleScope.launch {
            time.collect{
                binding.timerTextView.text = "$it seconds remains"
                if (it<10){
                    binding.timerTextView.setTextColor(Color.RED)
                }

                if (it == 0){
                    gameMode = GameMode.FINISHED
                    binding.points.text = "Game over"
                    binding.btnClose.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun begingTimerCountDown() {
        lifecycleScope.launch {
            for (i in 0..19){
                delay(1000)
                val currentTime = time.value
                time.emit(currentTime-1)
            }
        }
    }

    private fun showCompliments() {
        binding.compliment.visibility = View.VISIBLE
        binding.compliment.setTextColor(Color.GREEN)
        binding.compliment.text = getRandomCompliment()

        val scaleX = ObjectAnimator.ofFloat(binding.compliment, "scaleX", 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(binding.compliment, "scaleY", 1f, 0f)
        val alpha = ObjectAnimator.ofFloat(binding.compliment, "alpha", 1f, 0f)

        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY, alpha)
        set.duration = 3000 // Set your desired duration

        set.start()

    }

    private fun setButtonsClickListenners() {
        binding.btnShut.setOnClickListener {
            if (gameMode == GameMode.PLAYING){
                shut(binding.jumpingBall)
            }
        }

        binding.btnRight.setOnClickListener {
            val newOffset = xOffset + halfOfTheScreenWidth / 5
            if (newOffset in -halfOfTheScreenWidth..halfOfTheScreenWidth) {
                xOffset = newOffset
                binding.platform.translationX = xOffset
                binding.jumpingBall.translationX = xOffset
            }
        }

        binding.btnLeft.setOnClickListener {
            val newOffset = xOffset - halfOfTheScreenWidth / 5
            if (newOffset in -halfOfTheScreenWidth..halfOfTheScreenWidth) {
                xOffset = newOffset
                binding.platform.translationX = xOffset
                binding.jumpingBall.translationX = xOffset
            }
        }

        binding.btnClose.setOnClickListener {
            val intentToTheMenu = Intent(this, DosActivity::class.java)
            startActivity(intentToTheMenu)
        }
    }


    private fun shut(imageView: ImageView) {
        imageView.post {
            val yPositionOfBall = binding.jumpingBall.y
            binding.jumpingBall.animate()
                .translationY(-yPositionOfBall)
                .setDuration(300)
                .withEndAction {

                    val greenElementBounds = Rect()
                    binding.greenElement.getGlobalVisibleRect(greenElementBounds)

                    val jumpingBallBounds = Rect()
                    binding.jumpingBall.getGlobalVisibleRect(jumpingBallBounds)

                    if (jumpingBallBounds.intersect(greenElementBounds)) {
                        //int
                        animateExplosion(binding.greenElement)

                        lifecycleScope.launch {
                            val currentScores = scores.first()
                            scores.emit(currentScores+1)

                            delay(1000)
                            binding.greenElement.alpha = 1F
                            binding.greenElement.scaleX = 1F
                            binding.greenElement.scaleY = 1F
                            binding.greenElement.translationX = getRandomXPosition()
                            binding.greenElement.setImageResource(getRandomImageRes())
                        }
                    }

                    binding.jumpingBall.translationY = 0F
                }
                .start()
        }
    }

    private fun animateExplosion(imageView: ImageView) {
        val scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0f)
        val alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f)

        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY, alpha)
        set.duration = 500 // Set your desired duration

        set.start()
    }

    private fun getRandomXPosition(): Float {
        return Random.nextFloat() * (halfOfTheScreenWidth * 2F) - halfOfTheScreenWidth
    }

    private fun getRandomImageRes(): Int {
        val listOfImages = listOf(
            R.drawable.bbkgreen,
            R.drawable.bbkred
        )
        return listOfImages[Random.nextInt(listOfImages.size)]
    }

    private fun getRandomCompliment(): String{
        val listOfCompliments = listOf(
            "Very good",
            "Perfect",
            "Amazing",
            "Nice, you are awesome",
            "Coooool !!!"
        )
        return listOfCompliments[Random.nextInt(listOfCompliments.size)]
    }
}

enum class GameMode{
    PLAYING, FINISHED
}