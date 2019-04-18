package com.nickjgski.touchcameratutorial

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

class TouchHandler(var mainActivity: MainActivity) : View.OnTouchListener {
    //we will need to this to interpret onLongPress and onDoubleTap
    var gestureDetectorCompat: GestureDetectorCompat = GestureDetectorCompat(this.mainActivity,
        MyGestureListener(mainActivity))
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val maskedAction = event.actionMasked
        gestureDetectorCompat.onTouchEvent(event)
        when (maskedAction) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                var i = 0
//number of points
                val size = event.pointerCount
                while (i < size) {
//finger ID
                    val id = event.getPointerId(i)
//updating path: MainActivity->MyView->(invalidate)->XML-> your eyes
                    mainActivity.addNewPath(id, event.getX(i), event.getY(i))
                    i++
                }
            }
            MotionEvent.ACTION_MOVE -> {
                var i = 0
                val size = event.pointerCount
                while (i < size) {
                    val id = event.getPointerId(i)
                    mainActivity.updatePath(id, event.getX(i), event.getY(i))
                    i++
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                var i = 0
                val size = event.pointerCount
                while (i < size) {
                    val id = event.getPointerId(i)
                    mainActivity.removePath(id)
                    i++
                }
            }
        }
        return true
    }
//inner class definiteion for the gesture listener that will help us interpret onDoubleTap and onLongPress
    private class MyGestureListener(var mainActivity: MainActivity) :
        GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
//passing it to Activity
            mainActivity.onDoubleTap()
            return super.onDoubleTap(e)
        }
        override fun onLongPress(e: MotionEvent) {
//passing it to Activity
            mainActivity.onLongPress()
            super.onLongPress(e)
        }
    }
}