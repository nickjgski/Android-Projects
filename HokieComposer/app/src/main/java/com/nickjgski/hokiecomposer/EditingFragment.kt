package com.nickjgski.hokiecomposer


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController


class EditingFragment : Fragment() {

    companion object {
        const val INIT_STATUS = "Initialization started"
        const val MUSIC_PLAYING = "music playing"
    }
    
    private lateinit var musicSpinner: Spinner
    private lateinit var effect1Spinner: Spinner
    private lateinit var effect2Spinner: Spinner
    private lateinit var effect3Spinner: Spinner
    private lateinit var effect1SeekBar: SeekBar
    private lateinit var effect2SeekBar: SeekBar
    private lateinit var effect3SeekBar: SeekBar
    var selectedMusic: Int = -1
    var effect1: Int = -1
    var effect2: Int = -1
    var effect3: Int = -1
    private var effect1start: Int = 0
    private var effect2start: Int = 0
    private var effect3start: Int = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editing, container, false)
        musicSpinner = view.findViewById(R.id.music_spinner)
        effect1Spinner = view.findViewById(R.id.effect1_spinner)
        effect2Spinner = view.findViewById(R.id.effect2_spinner)
        effect3Spinner = view.findViewById(R.id.effect3_spinner)
        effect1SeekBar = view.findViewById(R.id.effect1_seekbar)
        effect2SeekBar = view.findViewById(R.id.effect2_seekbar)
        effect3SeekBar = view.findViewById(R.id.effect3_seekbar)
        setBoundaries()

        ArrayAdapter.createFromResource(
            view.context,
            R.array.background,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            musicSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            view.context,
            R.array.effects,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            effect1Spinner.adapter = adapter
            effect2Spinner.adapter = adapter
            effect3Spinner.adapter = adapter
        }



        musicSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect1Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect2Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect3Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect1SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                effect1start = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        effect2SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                effect2start = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        effect3SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                effect3start = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        var playButton = view.findViewById<Button>(R.id.play)
        playButton.setOnClickListener {
            if(selectedMusic != -1 && effect1 != -1 && effect2 != -1 && effect3 != -1) {
                view.findNavController().navigate(
                    R.id.action_editingFragment_to_playingFragment,
                    bundleOf("song" to)
                )
            }
        }
        
        return view
    }

    private fun setBoundaries() {

        effect1SeekBar.min = 0
        effect1SeekBar.max = 49
        effect2SeekBar.min = 0
        effect2SeekBar.max = 49
        effect3SeekBar.min = 0
        effect3SeekBar.max = 49

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(MUSIC_PLAYING, selectedMusic)
        outState.putInt("Effect1", effect1)
        outState.putInt("Effect2", effect2)
        outState.putInt("Effect3", effect3)
        super.onSaveInstanceState(outState)
    }

}
