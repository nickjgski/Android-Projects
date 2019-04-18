package com.nickjgski.hokiecomposer


import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MusicService: Service() {

    var musicPlayer: MusicPlayer? = null
    var effect1Player: MusicPlayer? = null
    var effect2Player: MusicPlayer? = null
    var effect3Player: MusicPlayer? = null


    companion object{
        val COMPLETE_INTENT = "complete intent"
        val MUSICNAME = "music name"}


    inner class MyBinder: Binder(){
        fun getService():MusicService{
            return this@MusicService
        }
    }

    private val iBinder = MyBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return iBinder
    }

    fun onUpdateMusicName(musicName: String) {

        val intent = Intent(COMPLETE_INTENT)
        intent.putExtra(MUSICNAME, musicName)
        sendBroadcast(intent)
    }


    override fun onCreate() {
        super.onCreate()
        musicPlayer = MusicPlayer(this)
        effect1Player = MusicPlayer(this)
        effect2Player = MusicPlayer(this)
        effect2Player = MusicPlayer(this)
        Log.d("create", "created")
    }

    fun startMusic(song: Int) {
        Log.d("play button", "playing music")
        musicPlayer?.playMusic(song, false)
    }

    fun startEffect(num: Int, effect: Int) {
        when(num) {
            0 -> effect1Player?.playMusic(effect, true)
            1 -> effect2Player?. playMusic (effect, true)
            2 -> effect3Player?.playMusic(effect, true)
        }
    }

    fun pauseMusic(num: Int) {
        when(num) {
            3 -> musicPlayer?.pauseMusic()
            0 -> effect1Player?.pauseMusic()
            1 -> effect2Player?.pauseMusic()
            2 -> effect3Player?.pauseMusic()
        }
    }

    fun resumeMusic(num: Int) {
        when(num) {
            3 -> musicPlayer?.resumeMusic()
            0 -> effect1Player ?.resumeMusic()
            1 -> effect2Player ?.resumeMusic()
            2 -> effect3Player ?.resumeMusic()
        }
    }
    
    fun restartMusic(num: Int) {
        when(num) {
            3 -> musicPlayer?.restartMusic()
            0 -> effect1Player ?.restartMusic()
            1 -> effect2Player ?.restartMusic()
            2 -> effect3Player ?.restartMusic()
        }
    }

    fun getPlayingStatus(): Int {
        return musicPlayer?.getMusicStatus() ?: -1
    }

    fun stopMusic() {

        musicPlayer?.stopMusic()
        effect1Player?.stopMusic()
        effect2Player?.stopMusic()
        effect3Player?.stopMusic()
    }

}