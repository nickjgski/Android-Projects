package com.nickjgski.hokiecomposer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.IOException





class MusicPlayer(val musicService: MusicService): MediaPlayer.OnCompletionListener {

    val SONGS = arrayOf(R.raw.gotechgo, R.raw.mario, R.raw.tetris)
    val EFFECTS = arrayOf(R.raw.cheering, R.raw.clapping, R.raw.lestgohokies)

    val MUSICNAME = arrayOf("Go Tech Go", "Super Mario", "Tetris")


    lateinit var player: MediaPlayer
    var currentPosition = 0
    var musicIndex = 0
    private var musicStatus = 0//0: before starts 1: playing 2: paused

    fun getMusicStatus(): Int {
        return musicStatus
    }

    fun getMusicName(): String {
        return MUSICNAME[musicIndex]
    }

    private fun getSound(song: Int, effect: Boolean): Int {

        if(effect) {
            return EFFECTS[song]
        } else {
            return SONGS[song]
        }
    }

    fun playMusic(song: Int, effect: Boolean) {
        Log.d("play button", "playing")
        player = MediaPlayer.create(musicService.baseContext, getSound(song, effect))
        try {
            player.setOnCompletionListener(this)
            player.start()
            musicService.onUpdateMusicName(getMusicName())
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        musicStatus = 1
    }

    fun pauseMusic() {
        if (player.isPlaying) {
            player.pause()
            currentPosition = player.currentPosition
            musicStatus = 2
        }
    }

    fun resumeMusic() {
        if(musicStatus == 2) {
            player.seekTo(currentPosition)
            player.start()
            musicStatus = 1
        }
    }

    fun restartMusic() {
        player.pause()
        player.seekTo(0)
        player.start()
        musicStatus = 1
    }

    override fun onCompletion(mp: MediaPlayer?) {
        player.release()
    }
}

