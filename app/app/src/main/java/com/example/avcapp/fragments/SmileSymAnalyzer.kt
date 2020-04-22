package com.example.avcapp.fragments

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

typealias SymListener = (angleDiff: Double) -> Unit

class SmileSymAnalyzer(listener : SymListener? = null) : ImageAnalysis.Analyzer {

    private val listener = listener //this should be something that draws on top of the camera
    private var detector: FirebaseVisionFaceDetector? = null

    fun myinit() {

        val highAccOpts :FirebaseVisionFaceDetectorOptions =
            FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .build()

        detector = FirebaseVision.getInstance().getVisionFaceDetector(highAccOpts)

    }

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy?.image
        if (mediaImage != null) {
            val image = FirebaseVisionImage.fromMediaImage(mediaImage,0)

            // Pass image to an ML Kit Vision API
            // ...
            listener?.invoke(0.0)
        }
        imageProxy.close()

    }

}