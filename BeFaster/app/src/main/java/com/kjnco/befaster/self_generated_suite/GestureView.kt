package com.kjnco.befaster.self_generated_suite

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

enum class GestureType {
    HAUT,
    BAS,
    GAUCHE,
    DROITE,
    NONE
}

class GestureView (context: Context, attrs: AttributeSet?): View(context, attrs) {

    private var lastX = 0f
    private var lastY = 0F
    private var numberOfDrawings = 0
    private var gestureDirection = GestureType.NONE
    private var gesturePath = Path()
    private val gesturePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 5f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
                gesturePath.moveTo(lastX, lastY)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                gesturePath.lineTo(x, y)
                detectGestureDirection(x, y)
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                numberOfDrawings++
                gesturePath.reset()
                gestureDirection = GestureType.NONE
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(gesturePath, gesturePaint)
    }

    private fun detectGestureDirection(currentX: Float, currentY: Float) {
        val deltaX = currentX - lastX
        val deltaY = currentY - lastY
        gestureDirection = if(kotlin.math.abs(deltaX) > kotlin.math.abs(deltaY)) {
            if (deltaX > 0) GestureType.DROITE else GestureType.GAUCHE
        }else {
            if (deltaY > 0) GestureType.BAS else GestureType.HAUT
        }
        lastX = currentX
        lastY = currentY
    }

    fun getGestureDirection(): GestureType {
        return gestureDirection
    }

    fun getNumberOfDrawings(): Int {
        return numberOfDrawings
    }

}