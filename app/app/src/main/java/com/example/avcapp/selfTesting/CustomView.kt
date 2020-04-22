package com.example.avcapp.selfTesting

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.google.firebase.ml.vision.common.FirebaseVisionPoint

class CustomView @JvmOverloads constructor(context: Context,
                                           attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    var mouthLeft: Point = Point()
    var mouthRight: Point = Point()
    var mouthBottom: Point = Point()
    var noseBase: Point = Point()

    public fun setPosition( mouthLeft : FirebaseVisionPoint     ,
                            mouthRigth : FirebaseVisionPoint    ,
                            mouthBottom : FirebaseVisionPoint   ,
                            noseBase: FirebaseVisionPoint       ) {
        this.mouthLeft = transformToCanvasCoords(mouthLeft)
        this.mouthRight = transformToCanvasCoords(mouthRigth)
        this.mouthBottom = transformToCanvasCoords(mouthBottom)
        this.noseBase = transformToCanvasCoords(noseBase)
    }

    private fun transformToCanvasCoords(fbPoint : FirebaseVisionPoint): Point {
        //todo
        return Point()
    }

    // Called when the view should render its content.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(Rect(0,0, 100, 100), squarePaint)
        // DRAW STUFF HERE
    }

    companion object {
        var squarePaint =
            Paint().apply {
                isAntiAlias = true
                color = Color.BLUE
                style = Paint.Style.FILL
            }
    }
}