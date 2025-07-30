package com.example.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PaintView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint: Paint = Paint()
    private val path: Path = Path()
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private val paths: MutableList<Triple<Path, Int, Float>> = mutableListOf()
    private var currentColor: Int = Color.BLACK
    private var currentStrokeWidth: Float = 10f

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.alpha = 128
    }

    private fun initCanvas() {
        if (width > 0 && height > 0) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap)
            canvas.drawColor(Color.TRANSPARENT)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initCanvas()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        for ((path, color, size) in paths) {
            paint.color = color
            paint.strokeWidth = size
            canvas.drawPath(path, paint)
        }

        paint.color = currentColor
        paint.strokeWidth = currentStrokeWidth
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                paths.add(Triple(Path(path), currentColor, currentStrokeWidth))
                path.reset()
                invalidate()
            }

            else -> return false
        }
        return true
    }

    fun clearCanvas() {
        initCanvas()
        paths.clear()
        invalidate()
    }

    fun setColor(newColor: Int, newSize: Float) {
        currentColor = newColor
        currentStrokeWidth = newSize
        invalidate()
    }
}
