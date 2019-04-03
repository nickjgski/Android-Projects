package com.nickjgski.hokiecomposer

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.fragment_editing.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val INITIALIZE_STATUS = "intialization status"
    val MUSIC_PLAYING = "music playing"

    //Java 'Timer'
    private var timer = Timer()

    //1000ms
    private val ONE_SECOND = 1000

    var selectedMusic: Int = 0
    var effect1: Int = 0
    var effect2: Int = 0
    var effect3: Int = 0
    var effect1start: Int = 0
    var effect2start: Int = 0
    var effect3start: Int = 0

    var counter1: Int = 0
    var counter2: Int = 0
    var counter3: Int = 0

    var countdown1 = MutableLiveData<Int>()
    var countdown2 = MutableLiveData<Int>()
    var countdown3 = MutableLiveData<Int>()

    init {
        countdown1.value = 0
        countdown2.value = 0
        countdown3.value = 0
    }


    var musicService: MusicService? = null
    var musicCompletionReceiver: MusicCompletionReceiver? = null
    var startMusicServiceIntent: Intent? = null
    var isInitialized = false
    var isBound = false

    private val musicServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {

            val binder = iBinder as MusicService.MyBinder
            musicService = binder.getService()
            isBound = true

        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            musicService = null
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //this restores the textview
        if (savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS)
            selectedMusic = savedInstanceState.getInt(MUSIC_PLAYING)
        }
        startMusicServiceIntent = Intent(this, MusicService::class.java)
        if (!isInitialized) {
            startService(startMusicServiceIntent)
            isInitialized = true
        }
        musicCompletionReceiver = MusicCompletionReceiver(this)

        if (isBound) {
            when (musicService?.getPlayingStatus()) {
                0 -> {
                    musicService?.startMusic(selectedMusic)
                }
                1 -> {
                    musicService?.pauseMusic(3)
                    if(countdown1.value!! >= effect1start) {
                        musicService?.pauseMusic(0)
                    }
                    if(countdown2.value!! >= effect2start) {
                        musicService?.pauseMusic(1)
                    }
                    if(countdown3.value!! >= effect3start) {
                        musicService?.pauseMusic(2)
                    }
                }
                2 -> {
                    musicService?.resumeMusic(3)
                    if(countdown1.value!! >= effect1start) {
                        musicService?.resumeMusic(0)
                    }
                    if(countdown2.value!! >= effect2start) {
                        musicService?.resumeMusic(1)
                    }
                    if(countdown3.value!! >= effect3start) {
                        musicService?.resumeMusic(2)
                    }
                }
            }
        }

    }

    fun startMusic() {

        musicService?.startMusic(selectedMusic)
        start()

    }

    private fun startEffect(num: Int, effect: Int) {
        musicService?.startEffect(num, effect)

    }

    fun pauseMusic() {

        musicService?.pauseMusic(3)
        timer.cancel()
        if(countdown1.value!! >= effect1start) {
            musicService?.pauseMusic(0)
        }
        if(countdown2.value!! >= effect2start) {
            musicService?.pauseMusic(1)
        }
        if(countdown3.value!! >= effect3start) {
            musicService?.pauseMusic(2)
        }

    }

    fun resumeMusic() {
        timer = Timer()
        musicService?.resumeMusic(3)
        if(countdown1.value!! >= effect1start) {
            musicService?.resumeMusic(0)
        }
        if(countdown2.value!! >= effect2start) {
            musicService?.resumeMusic(1)
        }
        if(countdown3.value!! >= effect3start) {
            musicService?.resumeMusic(2)
        }
        start()
    }

    fun restartMusic() {
        musicService?.restartMusic(3)
        timer.cancel()
        if(countdown1.value!! >= effect1start) {
            counter1 = 0
            countdown1.postValue(counter1)
            musicService?.resumeMusic(0)
        }
        if(countdown2.value!! >= effect2start) {
            counter2 = 0
            countdown2.postValue(counter2)
            musicService?.resumeMusic(1)
        }
        if(countdown3.value!! >= effect3start) {
            counter2 = 0
            countdown2.postValue(counter2)
            musicService?.resumeMusic(2)
        }
        timer = Timer()
        start()
    }

    fun setSelections(song: Int, effect1: Int, effect2: Int, effect3: Int, time1: Int, time2: Int, time3: Int) {
        selectedMusic = song
        this.effect1 = effect1
        this.effect2 = effect2
        this.effect3 = effect3
        effect1start = time1
        effect2start = time2
        effect3start = time3
    }

    fun getStatus(): Int? {
        if(isBound) {
            return musicService?.getPlayingStatus()
        }
        return null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(MUSIC_PLAYING, selectedMusic)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        if (isBound) {
            unbindService(musicServiceConnection)
            isBound = false
        }
        unregisterReceiver(musicCompletionReceiver)
    }

    override fun onResume() {
        super.onResume()
        if (isInitialized && !isBound) {
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE)
        }
        registerReceiver(musicCompletionReceiver, IntentFilter(MusicService.COMPLETE_INTENT))
    }

    private fun start(){
        // Option 1 -- Timer : Update the elapsed time every second.
        timer.scheduleAtFixedRate(object : TimerTask() {

            // runs on a non-UI thread.
            override fun run() {

                if(countdown1.value == effect1start) {
                    startEffect(0, effect1)
                }
                if(countdown2.value == effect2start) {
                    startEffect(1, effect2)
                }
                if(countdown3.value == effect3start) {
                    startEffect(2, effect3)
                }

                counter1++
                counter2++
                counter3++
                //'posting' new values every second to the MutableLiveData object
                // notice that this is happening on a non-UI thread.
                countdown1.postValue(counter1)
                countdown2.postValue(counter2)
                countdown3.postValue(counter3)


            }
            //delay              //period
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

}
