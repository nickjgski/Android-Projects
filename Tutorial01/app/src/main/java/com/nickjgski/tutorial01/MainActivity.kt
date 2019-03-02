package com.nickjgski.tutorial01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var usdSeekBar: SeekBar? = null
    var lbsSeekBar : SeekBar? = null
    var usdTotal: TextView? = null
    var lbsTotal: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usdSeekBar = findViewById(R.id.usdSeekBar)
        lbsSeekBar = findViewById(R.id.lbsSeekBar)
        usdTotal = findViewById(R.id.usdLabel)
        lbsTotal = findViewById(R.id.lbsLabel)

        setBoundaries()

        usdSeekBar?.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener(

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                usdTotal?.text = "USD:$progress"
                findViewById<TextView>(R.id.total).text = "Total cost: $progress"

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        ))

    }

    private fun setBoundaries() {

        usdSeekBar?.min = 1
        usdSeekBar?.max = 125
        lbsSeekBar?.min = 1
        lbsSeekBar?.max = 100

    }

}
