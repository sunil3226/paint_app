package com.example.paint

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paint()

        var tools = findViewById<LinearLayout>(R.id.tools)
        findViewById<Button>(R.id.visibilityButton).setOnClickListener {
            if (tools.visibility == View.VISIBLE) {
                tools.visibility = View.GONE
            } else {
                tools.visibility = View.VISIBLE
            }
        }

    }

    protected fun paint() {

        var paintView : PaintView = findViewById(R.id.paintView)

         findViewById<ImageButton>(R.id.pencil).setOnClickListener {
            val color = ContextCompat.getColor(this, R.color.black)
            paintView.setColor(color, 10f)
        }

        findViewById<ImageButton>(R.id.highlighter).setOnClickListener {
            paintView.setColor(Color.argb(100, 255, 255, 0), 70f)
        }

        findViewById<ImageButton>(R.id.eraser).setOnClickListener {
            val color = ContextCompat.getColor(this, R.color.white)
            paintView.setColor(color, 30f)
        }

        findViewById<ImageButton>(R.id.clearButton).setOnClickListener {
            paintView.clearCanvas()
        }

    }

}