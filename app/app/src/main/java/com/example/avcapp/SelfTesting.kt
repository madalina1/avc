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

const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"
private const val IMMERSIVE_FLAG_TIMEOUT = 500L

class SelfTesting : AppCompatActivity() {

    private var backPressed: Boolean? = null
    private lateinit var container: FrameLayout

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

//        container = findViewById(R.id.fragment_container)
//        FirebaseApp.initializeApp(applicationContext)
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

//    override fun onResume() {
//        super.onResume()
//        // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
//        // be trying to set app to immersive mode before it's ready and the flags do not stick
//        container.postDelayed({
//            container.systemUiVisibility = FLAGS_FULLSCREEN
//        }, IMMERSIVE_FLAG_TIMEOUT)
//    }
//
//    /** When key down event is triggered, relay it via local broadcast so fragments can handle it */
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        return when (keyCode) {
//            KeyEvent.KEYCODE_VOLUME_DOWN -> {
//                val intent = Intent(KEY_EVENT_ACTION).apply { putExtra(KEY_EVENT_EXTRA, keyCode) }
//                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
//                true
//            }
//            else -> super.onKeyDown(keyCode, event)
//        }
//    }
//
//    companion object {
//
//        /** Use external media if it is available, our app's file directory otherwise */
//        fun getOutputDirectory(context: Context): File {
//            val appContext = context.applicationContext
//            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
//                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
//            }
//            return if (mediaDir != null && mediaDir.exists())
//                mediaDir else appContext.filesDir
//        }
//    }
}
