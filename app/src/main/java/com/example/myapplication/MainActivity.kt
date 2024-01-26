package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView: ImageView = findViewById(R.id.imageView)
        val playerImage: ImageView = findViewById(R.id.imageView2)
        val celebrationImage: ImageView = findViewById(R.id.imageView4)
        val button: Button = findViewById(R.id.button)
        playerImage.bringToFront()
        imageView.bringToFront()
        celebrationImage.visibility = View.GONE

        val animationSet = AnimationSet(false)
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
        val movePlayerAnimation = AnimationUtils.loadAnimation(this, R.anim.move_player)
        val moveBallAnimation = AnimationUtils.loadAnimation(this, R.anim.move_ball_animation)
        val celebrationAnim = AnimationUtils.loadAnimation(this, R.anim.celebration_animation)
        animationSet.addAnimation(rotateAnimation)
        animationSet.addAnimation(moveBallAnimation)

        button.setOnClickListener {
            imageView.startAnimation(animationSet)
            playerImage.startAnimation(movePlayerAnimation)
            celebrationImage.visibility = View.VISIBLE
            celebrationImage.bringToFront()
            celebrationImage.startAnimation(celebrationAnim)
        }
    }
}