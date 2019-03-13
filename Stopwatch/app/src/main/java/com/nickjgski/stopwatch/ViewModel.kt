package com.nickjgski.stopwatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class ViewModel: ViewModel(){

    //Java 'Timer'
    private var timer = Timer()

    //Live data for passing seconds to DisplayFragment
    private val mElapsedTime = MutableLiveData<Long>()

    //Live data for saving laps
    private val lapList = ArrayList<Long>()
    private val mListSize = MutableLiveData<Int>()



    //1000ms
    private val ONE_SECOND = 1000
    private var counter: Long = 0
    private var running = false

    //constructor call
    init {
        mElapsedTime.value = 0
        mListSize.value = 0
    }

    //ControlFragment will invoke this
    fun startstop(){
        if(running){
            timer.cancel()
            running=!running
        }
        else{
            //after calling 'cancel' on timer we need to create a new instance
            timer = Timer()
            start()
            running=!running
        }
    }

    fun reset() {
        if(running) {
            timer.cancel()
            running = !running
        }
        mElapsedTime.postValue(0)
        counter = 0
        lapList.clear()
        mListSize.postValue(lapList.size)
    }

    private fun start(){
        // Option 1 -- Timer : Update the elapsed time every second.
        timer.scheduleAtFixedRate(object : TimerTask() {

            // runs on a non-UI thread.
            override fun run() {

                counter++
                //'posting' new values every second to the MutableLiveData object
                // notice that this is happening on a non-UI thread.
                mElapsedTime.postValue(counter)
            }
            //delay              //period
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }



    //DisplayFragment will use this method to retrieve the live data object.
    fun getElapsedTime(): LiveData<Long> {
        return mElapsedTime
    }



    private val viewModelJob = Job()

    //the scope is a must in order to ensure friendly behavior
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    fun slowLapCapture() {


        ioScope.launch { // launch new coroutine in background and continue
            updateLiveData(mElapsedTime.value)
        }
    }

    private fun updateLiveData(lap:Long?){
        lap?.let { lapList.add(it) }
        mListSize.postValue(lapList.size)
    }
    
    fun getListSize():LiveData<Int> {
        return mListSize
    }

    fun getLapTimes():ArrayList<Long>{
        return lapList
    }


}
