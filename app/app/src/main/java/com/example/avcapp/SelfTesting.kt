package com.example.avcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.avcapp.utils.makeStatusBarTransparent

class SelfTesting : AppCompatActivity() {

    private var backPressed: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_testing)

        makeStatusBarTransparent()

        this.backPressed = false;
    }

    override fun onBackPressed() {
        if (!backPressed!!) {
            backPressed = true
            Toast.makeText(
                applicationContext,
                R.string.back_pressed_message,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            backPressed = false
            super.onBackPressed()
        }
    }
}
