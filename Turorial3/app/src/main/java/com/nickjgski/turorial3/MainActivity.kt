package com.nickjgski.turorial3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private val repetitions = 10
    private val delay: Long = 1000
    private var coroutineJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start).setOnClickListener {
            slotMachineDraw(findViewById(R.id.left), findViewById(R.id.middle), findViewById(R.id.right))
        }

        findViewById<Button>(R.id.stop).setOnClickListener{
            coroutineJob?.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineJob?.cancel()
    }

    // generate ints
    // @params: interval, frequency and textview
    suspend fun randomInt(repetitions: Int, delayDuration: Long, v: TextView): Int {

        var rand = -1
        for(i: Int in 0..repetitions) {
            delay(delayDuration)
            rand = (0..10).random()

            Log.d("slotmachine", "Thread:"+Thread.currentThread().name)
            //Updates UI, creates portal to access main thread
            withContext(Dispatchers.Main) {
                v.text = rand.toString()
            }
        }
        return rand

    }

    fun slotMachineDraw(left: TextView, middle: TextView, right: TextView) {

        coroutineJob?.cancel()

        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            val leftDeferred = async {
                randomInt(repetitions, delay, left)
            }

            val middleDeferred = async {
                randomInt(repetitions, delay, middle)
            }

            val rightDeferred = async {
                randomInt(repetitions, delay, right)
            }

            val left_final = leftDeferred.await()
            val middle_final = middleDeferred.await()
            val right_final = rightDeferred.await()

            withContext(Dispatchers.Main) {
                left.text = "-$left_final-"
                middle.text = "-$middle_final"
                right.text = "-$right_final"
            }
        }

    }
}
