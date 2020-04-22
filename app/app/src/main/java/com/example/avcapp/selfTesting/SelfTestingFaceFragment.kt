package com.example.avcapp.selfTesting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.android.example.cameraxbasic.utils.FLAGS_FULLSCREEN
import com.example.avcapp.R
import com.google.firebase.FirebaseApp
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelfTestingFaceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
const val KEY_EVENT_ACTION = "key_event_action"
const val KEY_EVENT_EXTRA = "key_event_extra"
private const val IMMERSIVE_FLAG_TIMEOUT = 500L

class SelfTestingFaceFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment\
        val view = inflater.inflate(R.layout.fragment_self_testing_face, container, false) as View

        this.container = view.findViewById(R.id.fragment_container)
        context?.let { FirebaseApp.initializeApp(it) }

        return view
    }

    override fun onResume() {
        super.onResume()
        // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
        // be trying to set app to immersive mode before it's ready and the flags do not stick
        container.postDelayed({
            container.systemUiVisibility = FLAGS_FULLSCREEN
        }, IMMERSIVE_FLAG_TIMEOUT)
    }

//    /** When key down event is triggered, relay it via local broadcast so fragments can handle it */
//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        return when (keyCode) {
//            KeyEvent.KEYCODE_VOLUME_DOWN -> {
//                val intent = Intent(com.example.avcapp.KEY_EVENT_ACTION).apply { putExtra(com.example.avcapp.KEY_EVENT_EXTRA, keyCode) }
//                context?.let { LocalBroadcastManager.getInstance(it).sendBroadcast(intent) }
//                true
//            }
//            else -> super.onKeyDown(keyCode, event)
//        }
//    }

    companion object {
        /** Use external media if it is available, our app's file directory otherwise */
        fun getOutputDirectory(context: Context): File {
            val appContext = context.applicationContext
            val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
                File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
            }
            return if (mediaDir != null && mediaDir.exists())
                mediaDir else appContext.filesDir
        }

        fun newInstance(): Fragment {
            return SelfTestingFaceFragment()
        }
    }
}
