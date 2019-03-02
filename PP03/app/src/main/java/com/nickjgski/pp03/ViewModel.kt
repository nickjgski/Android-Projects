package com.nickjgski.pp03

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class Model: ViewModel() {


    private val delay: Long=1_000
    private val repetitions = 10
    private val valuesLiveData = MutableLiveData<List<Int>>()
    private val valuesLocal = mutableListOf(0,0,0)

    private var viewModelJob: Job
    private var ioScope: CoroutineScope

    init {
        viewModelJob = Job()
        ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
        valuesLiveData.value = valuesLocal
    }

    private val TAG = "slotmachine"

    fun getValues(): MutableLiveData<List<Int>> {

        return valuesLiveData

    }

    fun cancel() {
        viewModelJob.cancel()
    }

    //return a random number between 0 to 10
    suspend fun randomInt(repetitions: Int, delay: Long): Int {
        var rand = -1
        for(i: Int in 0..repetitions){

            delay(delay)

            rand = (0..10).random()

            Log.d(TAG, "Background -> "+Thread.currentThread().name)



        }
        return rand
    }

    fun slotMachineDraw()  {
        //cancel the job object in case user wants to start it again
        viewModelJob.cancel()
        // .launch returns a Job object that we can then use to cancel the coroutine
        viewModelJob = CoroutineScope(Dispatchers.IO).launch {

            // 'async' will let us run the randInt generators concurrently.
            // notice the parameters that we are passing to randomInt()
            val leftDeffered = async {
                randomInt(repetitions,delay)

            }

            val middleDeffered = async {
                randomInt(repetitions,delay)
            }

            val rightDeffered = async {
                randomInt(repetitions,delay)
            }


            // 'await' is needed to retrieve the final result once it is available
            val left_final = leftDeffered.await()
            val middle_final = middleDeffered.await()
            val right_final = rightDeffered.await()
            valuesLocal[0] = left_final
            valuesLocal[1] = middle_final
            valuesLocal[2] = right_final
            valuesLiveData.postValue(valuesLocal)


        }

    }



}