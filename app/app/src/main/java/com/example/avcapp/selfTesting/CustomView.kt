package com.example.avcapp.selfTesting

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class CustomView @JvmOverloads constructor(context: Context,
                                           attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    public fun setCol(col :Int) {
        squarePaint = Paint().apply {
            isAntiAlias = true
            color = col
            style = Paint.Style.FILL
        }
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