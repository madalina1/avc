package com.example.avcapp.otherTesting

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.avcapp.MainActivity
import com.example.avcapp.OtherTesting
import com.example.avcapp.R
import com.google.android.gms.maps.*
import java.util.*
import com.example.avcapp.utils.createMap

class OtherTestingTimeFragment : Fragment() {
    private var mMapView: MapView? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_other_testing_time, container, false) as View

        mMapView = rootView.findViewById(R.id.timeMapView)
        mMapView!!.onCreate(savedInstanceState)

        createMap(mMapView, requireActivity(), false)

        val testingActivity = activity as OtherTesting?
        var otherSymptoms: Short = 0
        if (testingActivity!!.dizziness) otherSymptoms++
        if (testingActivity.headache) otherSymptoms++
        if (testingActivity.paralysis) otherSymptoms++
        if (testingActivity.vision) otherSymptoms++
        if (testingActivity.confusion) otherSymptoms++
        if (testingActivity.convulsion) otherSymptoms++
        if (testingActivity.vomiting) otherSymptoms++
        if (testingActivity.deviation) otherSymptoms++

        var unknown: Short = 0
        if (testingActivity.faceResult.toInt() == 0) unknown++
        if (testingActivity.armsResult.toInt() == 0) unknown++
        if (testingActivity.speechResult.toInt() == 0) unknown++

        //Severe cases
        if (testingActivity.faceResult.toInt() == 1 || testingActivity.armsResult.toInt() == 1 || testingActivity.speechResult.toInt() == 1 || unknown >= 2 || otherSymptoms >= 4 || unknown >= 1 && otherSymptoms > 2) {
            (rootView.findViewById<View>(R.id.timeTitleResult) as TextView).text =
                getString(R.string.other_testing_time, "high")

            (rootView.findViewById<View>(R.id.timeTitleResult) as TextView).setTextColor(resources.getColor(R.color.riskColor))

            (rootView.findViewById<View>(R.id.timeTextLocation) as TextView).text =
                getString(R.string.other_testing_time_location, "You can go to the hospital immediately! ")

            (rootView.findViewById<View>(R.id.timeRisk) as ImageView).setImageDrawable(
                requireContext().getDrawable(R.drawable.risk)
            )

            (rootView.findViewById<View>(R.id.emergencyButton) as Button).background =
                requireContext().getDrawable(R.drawable.emergency_call_button_risk)

            val currentTime: Date = Calendar.getInstance().time
            val dateFormat: java.text.DateFormat? = DateFormat.getTimeFormat(context)
            val dateString: String? = dateFormat?.format(currentTime)
            val mNotificationManager: NotificationManager
            val mContext: Context? = context
            val mBuilder: Notification.Builder =
                Notification.Builder(mContext?.applicationContext, "notify_001")
            val ii = Intent(mContext?.applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0)
            val bigText: Notification.BigTextStyle = Notification.BigTextStyle()

            bigText.bigText(resources.getString(R.string.notification_big_text, "$dateString\n" +
                    "JUST Scores:\n" +
                    "Stroke - 3\n" +
                    "LVO - 7\n" +
                    "ICH - 1\n" +
                    "SAH - -4"))
            bigText.setBigContentTitle(resources.getString(R.string.notification_title))
            bigText.setSummaryText(resources.getString(R.string.app_name))
            mBuilder.setContentIntent(pendingIntent)
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
            mBuilder.setContentTitle(resources.getString(R.string.app_name))
            mBuilder.setContentText(resources.getString(R.string.notification_title))
            mBuilder.setPriority(Notification.PRIORITY_MAX)
            mBuilder.style = bigText
            mBuilder.setOngoing(true)
            mNotificationManager =
                mContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "Your_channel_id"
                val channel = NotificationChannel(
                    channelId,
                    resources.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                mNotificationManager.createNotificationChannel(channel)
                mBuilder.setChannelId(channelId)
            }
            mNotificationManager.notify(0, mBuilder.build())
        }

        //Medium cases
        else if (unknown.toInt() == 1 || otherSymptoms >= 2) {
            (rootView.findViewById<View>(R.id.timeTitleResult) as TextView).text = getString(R.string.other_testing_time, "medium")
            (rootView.findViewById<View>(R.id.timeRisk) as ImageView).setImageDrawable(
                requireContext().getDrawable(R.drawable.medium_risk)
            )

            (rootView.findViewById<View>(R.id.timeTitleResult) as TextView).setTextColor(resources.getColor(R.color.mediumRisk))

            (rootView.findViewById<View>(R.id.timeTextLocation) as TextView).text =
                getString(R.string.other_testing_time_location, "You can go to the hospital immediately! ")

            (rootView.findViewById<View>(R.id.emergencyButton) as Button).background =
                requireContext().getDrawable(R.drawable.emergency_call_button_medium)

            val currentTime: Date = Calendar.getInstance().time
            val dateFormat: java.text.DateFormat? = DateFormat.getTimeFormat(context)
            val dateString: String = dateFormat!!.format(currentTime)
            val mNotificationManager: NotificationManager
            val mContext: Context? = context
            val mBuilder: Notification.Builder =
                Notification.Builder(mContext?.applicationContext, "notify_001")
            val ii = Intent(mContext?.applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(mContext, 0, ii, 0)
            val bigText: Notification.BigTextStyle = Notification.BigTextStyle()

            bigText.bigText(resources.getString(R.string.notification_big_text, dateString))
            bigText.setBigContentTitle(resources.getString(R.string.notification_title))
            bigText.setSummaryText(resources.getString(R.string.app_name))
            mBuilder.setContentIntent(pendingIntent)
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
            mBuilder.setContentTitle(resources.getString(R.string.app_name))
            mBuilder.setContentText(resources.getString(R.string.notification_title))
            mBuilder.setPriority(Notification.PRIORITY_MAX)
            mBuilder.style = bigText
            mBuilder.setOngoing(true)
            mNotificationManager =
                mContext?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId = "Your_channel_id"
                val channel = NotificationChannel(
                    channelId,
                    resources.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH
                )
                mNotificationManager.createNotificationChannel(channel)
                mBuilder.setChannelId(channelId)
            }
            mNotificationManager.notify(0, mBuilder.build())
        }

        //Low cases
        else {
            (rootView.findViewById<View>(R.id.timeTitleResult) as TextView).text =
                getString(R.string.other_testing_time, "low")

            (rootView.findViewById<View>(R.id.timeRisk) as ImageView).setImageDrawable(
                requireContext().getDrawable(R.drawable.time_great)
            )
            (rootView.findViewById<View>(R.id.timeTextLocation) as TextView).text =
                getString(R.string.other_testing_time_location, "")

            val btn: Button =
                rootView.findViewById<View>(R.id.emergencyButton) as Button
            (btn.parent as ViewManager).removeView(btn)
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView!!.onLowMemory()
    }

    companion object {
        fun newInstance(): OtherTestingTimeFragment? {
            return OtherTestingTimeFragment()
        }
    }
}
