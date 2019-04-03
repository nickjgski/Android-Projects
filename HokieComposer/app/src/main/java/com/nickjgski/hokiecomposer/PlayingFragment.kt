package com.nickjgski.hokiecomposer


import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_editing.*
import kotlinx.android.synthetic.main.fragment_playing.*


class PlayingFragment : Fragment() {

    val INITIALIZE_STATUS = "intialization status"
    val MUSIC_PLAYING = "music playing"

    var playPause: Button? = null
    var restart: Button? = null

    val MUSICNAME = arrayOf("Go Tech Go", "Super Mario", "Tetris")

    var selectedMusic: Int? = 0
    var effect1: Int? = 0
    var effect2: Int? = 0
    var effect3: Int? = 0
    var effect1start: Int? = 0
    var effect2start: Int? = 0
    var effect3start: Int? = 0
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_playing, container, false)

        var selectedMusic: Int? = this.arguments?.getInt("song")
        var effect1: Int? = this.arguments?.getInt("effect1")
        var effect2: Int? = this.arguments?.getInt("effect2")
        var effect3: Int? = this.arguments?.getInt("effect3")
        var effect1start: Int? = this.arguments?.getInt("start1")
        var effect2start: Int? = this.arguments?.getInt("start2")
        var effect3start: Int? = this.arguments?.getInt("start3")
        mainActivity = activity as MainActivity
        mainActivity.setSelections(
            selectedMusic!!,
            effect1!!,
            effect2!!,
            effect3!!,
            effect1start!!,
            effect2start!!,
            effect3start!!
        )

        val songTitle = view.findViewById<TextView>(R.id.song_title)
        songTitle.text = MUSICNAME[selectedMusic!!]

        playPause = view.findViewById(R.id.play_pause)
        when(mainActivity.getStatus()) {
            0 -> playPause?.setText(R.string.play)
            1 -> playPause?.setText(R.string.pause)
            2 -> playPause?.setText((R.string.play))
        }
        playPause?.setOnClickListener {
            Log.d("play button", "play")
            when(mainActivity.getStatus()) {
                0 -> {
                    playPause?.setText(R.string.pause)
                    mainActivity.startMusic()
                }
                1 -> {
                    playPause?.setText(R.string.play)
                    mainActivity.pauseMusic()
                }
                2 -> {
                    playPause?.setText(R.string.pause)
                    mainActivity.resumeMusic()
                }
            }
        }

        restart = view.findViewById(R.id.restart)
        restart?.setOnClickListener {
            mainActivity.restartMusic()
        }

        return view
    }


}
