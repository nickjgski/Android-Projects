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
import android.widget.ImageView
import android.widget.TextView
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.android.synthetic.main.fragment_editing.*
import kotlinx.android.synthetic.main.fragment_playing.*


class PlayingFragment : Fragment() {

    val INITIALIZE_STATUS = "intialization status"
    val MUSIC_PLAYING = "music playing"

    var playPause: Button? = null
    var restart: Button? = null
    var image: ImageView? = null

    private val songImages = arrayOf(R.drawable.gotechgo, R.drawable.mario, R.drawable.tetris)
    private val effectImages = arrayOf(R.drawable.cheering, R.drawable.clapping, R.drawable.letsgohokies)
    private val MUSICNAME = arrayOf("Go Tech Go", "Super Mario", "Tetris")

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
        mainActivity = activity as MainActivity
        mainActivity.stopMusic()

        selectedMusic = this.arguments?.getInt("song")
        effect1 = this.arguments?.getInt("effect1")
        effect2 = this.arguments?.getInt("effect2")
        effect3 = this.arguments?.getInt("effect3")
        effect1start = this.arguments?.getInt("start1")
        effect2start = this.arguments?.getInt("start2")
        effect3start = this.arguments?.getInt("start3")

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
            appendEvent("Play/pause hit")
        }

        image = view.findViewById(R.id.songImage)


        restart = view.findViewById(R.id.restart)
        restart?.setOnClickListener {
            mainActivity.restartMusic()
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
            appendEvent("Restart")
        }

        return view
    }

    private fun appendEvent(event: String) {
        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(workDataOf("username" to MainActivity.USERNAME, "event" to event))
            .build()

        WorkManager.getInstance().beginUniqueWork(event, ExistingWorkPolicy.APPEND, uploadWorkRequest).enqueue()
    }


}
