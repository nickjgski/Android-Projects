package com.nickjgski.hokiecomposer


import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

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
    }

    fun startMusic(song: Int, effect1: Int, effect2: Int, effect3: Int) {
        musicPlayer?.playMusic(song, false)
        effect1Player?.playMusic(effect1, true)
        effect2Player?.playMusic(effect2, true)
        effect3Player?.playMusic(effect3, true)
    }

    fun pauseMusic() {
        musicPlayer?.pauseMusic()
        effect1Player?.pauseMusic()
        effect2Player?.pauseMusic()
        effect3Player?.pauseMusic()
    }

    fun resumeMusic() {
        musicPlayer?.resumeMusic()
        effect1Player?.resumeMusic()
        effect2Player?.resumeMusic()
        effect3Player?.resumeMusic()
    }

    fun getPlayingStatus(): Int {
        return musicPlayer?.getMusicStatus() ?: -1
    }

}