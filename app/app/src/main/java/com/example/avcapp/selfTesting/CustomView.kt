package com.example.avcapp.selfTesting

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.View
import com.google.firebase.ml.vision.common.FirebaseVisionPoint

class CustomView @JvmOverloads constructor(context: Context,
                                           attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    var mouthLeft: Point = Point()
    var mouthRight: Point = Point()
    var mouthBottom: Point = Point()
    var noseBase: Point = Point()
    var imgSize : Size = Size(1,1)

    public fun setPosition( mouthLeft : FirebaseVisionPoint     ,
                            mouthRigth : FirebaseVisionPoint    ,
                            mouthBottom : FirebaseVisionPoint   ,
                            noseBase: FirebaseVisionPoint       ) {
        this.mouthLeft = transformToCanvasCoords(mouthLeft)
        this.mouthRight = transformToCanvasCoords(mouthRigth)
        this.mouthBottom = transformToCanvasCoords(mouthBottom)
        this.noseBase = transformToCanvasCoords(noseBase)

        Log.d("Nose Pos", noseBase.toString())
    }

    private fun transformToCanvasCoords(fbPoint : FirebaseVisionPoint): Point {
        //todo
//
//        val microX = fbPoint.x / imgSize.height
//        val microY = fbPoint.y / imgSize.width
//        val targetX = (1.0F - microX) * this.height
//        val targetY = (1.0F - microY) * this.width
//
//        var pt= Point(targetX.toInt(), targetY.toInt())
////        var pt= Point(targetY.toInt(), targetX.toInt())
//        Log.d("Resulting pt ", pt.toString())


//        val canvasWidth = this.width
//        val canvasHeight = this.height




//        val scaleX = this.width.toFloat() / this.imgSize.width
//        val scaleY = this.height.toFloat() / this.imgSize.height




//        return Point((fbPoint.x * scaleX).toInt(), (fbPoint.y * scaleY).toInt())
//        return pt
//
//        val rotatedSize = Size(imgSize.width, imgSize.height)
//
//        val targetWidth = this.width
//        val targetHeight = this.height
//        val scaleX = this.width / imgSize.width.toDouble()
//        val scaleY = this.height / imgSize.height.toDouble()
//
//        val scale = Math.max(scaleX, scaleY)
//        val scaleF = scale.toFloat()
//        val scaledSize = Size(Math.ceil(rotatedSize.width * scale).toInt(), Math.ceil(rotatedSize.height * scale).toInt())
//
//        val offsetX = (targetWidth - scaledSize.width) / 2
//        val offsetY = (targetHeight - scaledSize.height) / 2
//
//        return Point((fbPoint.x * scaleF).toInt(), (fbPoint.y *scaleF).toInt())

        val y = (imgSize.width - fbPoint.x) / imgSize.width.toFloat() * this.height.toFloat()
        val x = (imgSize.height - fbPoint.y) / imgSize.height.toFloat() * this.width.toFloat()

        return Point(x.toInt(), y.toInt())



    }

    // Called when the view should render its content.
    override fun onDraw(canvas: Canvas) {
        Log.d("ImageSz in cw", imgSize.toString())
        Log.d("CanvasSz in cw", canvas.width.toString() + " " + canvas.height.toString())
        super.onDraw(canvas)
//        canvas.drawRect(Rect(this.noseBase.x, this.noseBase.y, this.noseBase.x + 100, this.noseBase.y-100), squarePaint)
//        canvas.drawRect(Rect(this.width, this.height, this.width + 100, this.height-100), squarePaintRed)
//        canvas.drawRect(Rect(0, 0,  100, 100), squarePaintRed)
//        canvas.drawCircle(0.0F, 0.0F, 200.0F, squarePaint)
//        canvas.drawCircle(width.toFloat(), height.toFloat(), 200.0F, squarePaint)
        canvas.drawCircle(this.noseBase.x.toFloat(), this.noseBase.y.toFloat(), 20.0F, squarePaint)
        canvas.drawCircle(this.mouthBottom.x.toFloat(), this.mouthBottom.y.toFloat(), 20.0F, squarePaint)
        canvas.drawCircle(this.mouthLeft.x.toFloat(), this.mouthLeft.y.toFloat(), 20.0F, squarePaint)
        canvas.drawCircle(this.mouthRight.x.toFloat(), this.mouthRight.y.toFloat(), 20.0F, squarePaint)
//        canvas.drawRect(Rect(this.noseBase.x, this.noseBase.y, this.noseBase.x + 100, this.noseBase.y+100), squarePaint)
//        canvas.drawRect(Rect(this.noseBase.y, this.noseBase.x, this.noseBase.y + 100, this.noseBase.x+100), squarePaint)
//        canvas.drawPoint(this.noseBase.x.toFloat(), this.noseBase.y.toFloat(), ptPaint)
//        canvas.drawPoint(this.noseBase.x.toFloat(), this.noseBase.y.toFloat(), ptPaint)
        // DRAW STUFF HERE
    }

    fun setImageSize(sz: Size) {
        this.imgSize = sz
    }

    companion object {
        var squarePaint =
            Paint().apply {
                isAntiAlias = true
                color = Color.BLUE
                style = Paint.Style.FILL
            }

        var squarePaintRed =
            Paint().apply {
                isAntiAlias = true
                color = Color.RED
                style = Paint.Style.FILL
            }

        var ptPaint =
            Paint().apply {
                isAntiAlias = true
                color = Color.BLUE
                style = Paint.Style.FILL_AND_STROKE
                strokeWidth = 3.0f

            }
    }
}