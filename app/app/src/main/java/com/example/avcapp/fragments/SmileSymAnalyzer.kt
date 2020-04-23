package com.example.avcapp.fragments

import android.util.Log
import android.util.Size
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionPoint
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark

typealias SymListener = (
    imgSize: Size,
    mouthLeft : FirebaseVisionPoint, mouthRigth : FirebaseVisionPoint,
    mouthBottom : FirebaseVisionPoint,nose : FirebaseVisionPoint) -> Unit

class SmileSymAnalyzer(listener : SymListener? = null) : ImageAnalysis.Analyzer {

    private val listener = listener //this should be something that draws on top of the camera
    private var detector: FirebaseVisionFaceDetector? = null

    init {
        val highAccOpts :FirebaseVisionFaceDetectorOptions =
            FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
//                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .build()

        detector = FirebaseVision.getInstance().getVisionFaceDetector(highAccOpts)

    }

    override fun analyze(imageProxy: ImageProxy) {

        val mediaImage = imageProxy?.image
        if (mediaImage != null) {
            val image = FirebaseVisionImage.fromMediaImage(mediaImage,0)
//            Log.d("imageSize", imageProxy.height.toString() + " " + imageProxy.width.toString())

            // Pass image to an ML Kit Vision API
            // ...
            val result = detector?.detectInImage(image)
                ?.addOnSuccessListener { faces ->
                    // Task completed successfully
                    // [START_EXCLUDE]
                    // [START get_face_info]
                    for (face in faces) {
                        val bounds = face.boundingBox
                        val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
                        val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees


                        listener?.invoke(
                            Size(image.bitmap.width, image.bitmap.height),
                            face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT)?.position!!,
                            face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT)?.position!!,
                            face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM)?.position!!,
                            face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)?.position!!


                        )
                        // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                        // nose available):
                        val leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)
                        leftEar?.let {
                            val leftEarPos = leftEar.position
                        }

                        // If classification was enabled:
                        if (face.smilingProbability != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                            val smileProb = face.smilingProbability
//                            listener?.invoke(smileProb.toDouble())
                        }
                        if (face.rightEyeOpenProbability != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                            val rightEyeOpenProb = face.rightEyeOpenProbability
                        }

                        // If face tracking was enabled:
                        if (face.trackingId != FirebaseVisionFace.INVALID_ID) {
                            val id = face.trackingId
                        }
                    }
                    // [END get_face_info]
                    // [END_EXCLUDE]
                }
                ?.addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }

        }
        imageProxy.close()

    }

}