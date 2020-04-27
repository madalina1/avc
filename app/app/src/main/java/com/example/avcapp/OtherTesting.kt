package com.example.avcapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.avcapp.otherTesting.*
import com.example.avcapp.utils.makeStatusBarTransparent


class OtherTesting : AppCompatActivity() {

    private var backPressed: Boolean? = null
    var faceResult: Short = -1
    var armsResult: Short = -1
    var speechResult: Short = -1
    var dizziness = false
    var headache = false
    var paralysis = false
    var vision = false
    var confusion = false
    var convulsion = false
    var vomiting = false
    var deviation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_testing)

        makeStatusBarTransparent()

        this.backPressed = false;

        if (savedInstanceState == null) {
            OtherTestingFaceFragment.newInstance()?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.otherTestingContainer, it)
                    .commitNow()
            };
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

    fun goToOtherTestingArms(view: View?) {
        OtherTestingArmsFragment.newInstance()?.let {
            supportFragmentManager.beginTransaction().replace(R.id.otherTestingContainer,
                it
            ).commitNow()
        };
    }

    fun goToOtherTestingSpeech(view: View?) {
        OtherTestingSpeechFragment.newInstance()?.let {
            supportFragmentManager.beginTransaction().replace(R.id.otherTestingContainer,
                it
            ).commitNow()
        };
    }

    fun goToOtherTestingTime(view: View) {
        if (view.id == R.id.other_symptoms_button) supportFragmentManager.beginTransaction()
            .replace(R.id.otherTestingContainer, OtherTestingTimeFragment.newInstance()!!).commitNow() else {
            var unknown: Short = 0
            if (faceResult.toInt() == 0) unknown++
            if (armsResult.toInt() == 0) unknown++
            if (speechResult.toInt() == 0) unknown++
            if (faceResult.toInt() == 1 || armsResult.toInt() == 1 || speechResult.toInt() == 1 || unknown >= 2) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.otherTestingContainer, OtherTestingTimeFragment.newInstance()!!).commitNow()
            } else {
                OtherTestingSymptomsFragment.newInstance()?.let {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.otherTestingContainer, it).commitNow()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onFaceRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        val nextFaceButton: Button = findViewById(R.id.other_face_button)
        nextFaceButton.isEnabled = true
        nextFaceButton.setTextColor(getColor(R.color.testing_title_color))

        when (view.getId()) {
            R.id.radio_face_yes -> if (checked) this.faceResult = 1
            R.id.radio_face_no -> if (checked) this.faceResult = -1
            R.id.radio_face_idk -> if (checked) this.faceResult = 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onArmsRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        val nextArmsButton =
            findViewById<View>(R.id.other_arms_button) as Button
        nextArmsButton.isEnabled = true
        nextArmsButton.setTextColor(getColor(R.color.testing_title_color))

        when (view.getId()) {
            R.id.radio_arms_yes -> if (checked) armsResult = 1
            R.id.radio_arms_no -> if (checked) armsResult = -1
            R.id.radio_arms_idk -> if (checked) armsResult = 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onSpeechRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        val nextSpeechButton =
            findViewById<View>(R.id.other_speech_button) as Button
        nextSpeechButton.isEnabled = true
        nextSpeechButton.setTextColor(getColor(R.color.testing_title_color))

        when (view.getId()) {
            R.id.radio_speech_yes -> if (checked) speechResult = 1
            R.id.radio_speech_no -> if (checked) speechResult = -1
            R.id.radio_speech_idk -> if (checked) speechResult = 0
        }
    }

    fun onOtherTestingCheckboxClicked(view: View) {
        val checked = (view as CheckBox).isChecked
        when (view.getId()) {
            R.id.checkbox_symptoms_dizziness -> dizziness = checked
            R.id.checkbox_symptoms_headache -> headache = checked
            R.id.checkbox_symptoms_confusion -> confusion = checked
            R.id.checkbox_symptoms_paralysis -> paralysis = checked
            R.id.checkbox_symptoms_vision -> vision = checked
            R.id.checkbox_symptoms_convulsion -> convulsion = checked
            R.id.checkbox_symptoms_vomiting -> vomiting = checked
            R.id.checkbox_symptoms_deviation -> deviation = checked
        }
    }

    fun callEmergency(view: View?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:112112")
        startActivity(intent)
    }
}
