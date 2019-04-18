package com.nickjgski.touchcameratutorial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class MyCanvas@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int =
    0) : View(context, attrs, defStyle) {
    //stroke thickness defined in the Paint object
    val strokeWidthMedium = 10f
    // this is where we map finger ID (Int) to Path (the line drawn on screen)
    var activePaths: HashMap<Int, Path> = HashMap()
    //setting up the Paint object. Notice the use of Kotlin's '.apply{}'
    var pathPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = strokeWidthMedium
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//drawing 'paths' from the set of active paths
        for (path in activePaths.values) {
            canvas?.drawPath(path, pathPaint)
        }
    }
    //starting point
    fun addPath(id: Int, x: Float, y: Float) {
        val path = Path()
        path.moveTo(x, y)
        activePaths[id] = path
//redraw the visuals
        invalidate()
    }
    //growing the path. Notice the use of .lineTo(x,y)
    fun updatePath(id: Int, x: Float, y: Float) {
        val path = activePaths[id]
        path?.lineTo(x, y)
        invalidate()
    }
    //this will remove the path for a given finger when it is lifted.
    fun removePath(id: Int) {
        if (activePaths.containsKey(id)) {
            activePaths.remove(id)
            invalidate()
        }
    }
}
