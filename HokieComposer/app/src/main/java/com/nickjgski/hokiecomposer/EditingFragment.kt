package com.nickjgski.hokiecomposer


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf


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
    private var selectedMusic: Int = 0
    private var effect1: Int = 0
    private var effect2: Int = 0
    private var effect3: Int = 0
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

        effect1SeekBar.min = 0
        effect2SeekBar.min = 0
        effect3SeekBar.min = 0

        musicSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMusic = position
                setBoundaries()
                appendEvent("Music changed")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect1Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                effect1 = position
                appendEvent("Effect 1 changed")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect2Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                effect2 = position
                appendEvent("Effect 2 changed")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect3Spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                effect3 = position
                appendEvent("Effect 3 changed")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        effect1SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                effect1start = progress
                appendEvent("Effect 1 timing changed")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        effect2SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                effect2start = progress
                appendEvent("Effect 2 timing changed")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        effect3SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                effect3start = progress
                appendEvent("Effect 3 timing changed")

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        val playButton = view.findViewById<Button>(R.id.play)
        playButton.setOnClickListener {
                view.findNavController().navigate(
                    R.id.action_editingFragment_to_playingFragment,
                    bundleOf("song" to selectedMusic, "effect1" to effect1, "effect2" to effect2,
                        "effect3" to effect3, "start1" to effect1start, "start2" to effect2start,
                        "start3" to effect3start)
                )
            appendEvent("Going to playing fragment")
        }

        return view
    }

    private fun setBoundaries() {

        when(selectedMusic) {
            0 -> {
                effect1SeekBar.max = 49
                effect2SeekBar.max = 49
                effect3SeekBar.max = 49
            }
            1 -> {
                effect1SeekBar.max = 27
                effect2SeekBar.max = 27
                effect3SeekBar.max = 27
            }
            2 -> {
                effect1SeekBar.max = 25
                effect2SeekBar.max = 25
                effect3SeekBar.max = 25
            }
            -1 -> {
                effect1SeekBar.max = 49
                effect2SeekBar.max = 49
                effect3SeekBar.max = 49
            }
        }

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

    private fun appendEvent(event: String) {
        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(workDataOf("username" to MainActivity.USERNAME, "event" to event))
            .build()

        WorkManager.getInstance().beginUniqueWork(event, ExistingWorkPolicy.APPEND, uploadWorkRequest).enqueue()
    }

}
