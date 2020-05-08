package com.example.avcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.avcapp.utils.makeStatusBarTransparent
import android.content.Context
import android.view.KeyEvent
import java.io.File
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.content.Intent
import android.widget.FrameLayout
import com.android.example.cameraxbasic.utils.FLAGS_FULLSCREEN
import com.example.avcapp.selfTesting.SelfTestingFaceFragment
import com.google.firebase.FirebaseApp

class SelfTesting : AppCompatActivity() {

    private var backPressed: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_testing)

        makeStatusBarTransparent()

        this.backPressed = false;

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SelfTestingFaceFragment.newInstance())
                .commitNow();
        }
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
