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
import com.kjnco.befaster.R

enum class GestureType {
    HAUT,
    BAS,
    GAUCHE,
    DROITE,
    NONE
}

interface GestureListener {
    fun onGestureDetected(gestureType: GestureType)
}

class GestureView (context: Context, attrs: AttributeSet?): View(context, attrs) {

    // Getting the attributes
    private val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.GestureView, 0, 0)

    // Retrieving the number of arrows to draw
    private var arrowsToDraw = typedArray.getInteger(R.styleable.GestureView_arrowsToDraw, 1)

    private var lastX = 0f
    private var lastY = 0F
    private var gestureDirection = GestureType.NONE
    private var gesturePath = Path()
    private val gesturePaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }
    private var numberOfArrows = 0

    // Gesture listener
    private var gestureListener: GestureListener? = null

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
                gesturePath.reset()
                if (numberOfArrows <= arrowsToDraw) {
                    if (gestureDirection != GestureType.NONE) {
                        synchronized(this) {
                            gestureListener?.onGestureDetected(gestureDirection)
                        }
                        invalidate()
                    }
                }
                gestureDirection = GestureType.NONE
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
            if (deltaX > 0){
                GestureType.DROITE
            } else{
                GestureType.GAUCHE
            }
        }else {
            if (deltaY > 0) {
                GestureType.BAS
            } else {
                GestureType.HAUT
            }
        }
        lastX = currentX
        lastY = currentY
    }

    fun clear() {
        gesturePath.reset()
        invalidate()
    }

    fun getNumberOfArrows(): Int {
        return numberOfArrows
    }

    fun setNumberOfArrows(arrows: Int) {
        this.numberOfArrows = arrows
    }

    fun incrementNumberOfArrows() {
        this.numberOfArrows++
    }

    fun setNumberOfArrowsToDraw(arrowsToDraw: Int) {
        this.arrowsToDraw = arrowsToDraw
    }

    fun getNumberOfArrowsToDraw(): Int {
        return this.arrowsToDraw
    }

    fun setGestureListener(gestureListener: GestureListener) {
        this.gestureListener = gestureListener
    }

}