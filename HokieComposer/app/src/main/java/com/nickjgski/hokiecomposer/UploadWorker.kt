package com.nickjgski.hokiecomposer


import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val json = JSONObject()
        json.put("username", inputData.getString("username"))
        json.put("event", inputData.getString("event"))
        Log.d(MainActivity.TAG, "params" + json.toString() + " url " + MainActivity.URL)
        return uploadLog(json, MainActivity.URL)

    }

    fun uploadLog(json: JSONObject, url: String): Result {

        Log.d(MainActivity.TAG, "uploadLog() " + url)
        val call: Call<String> = TrackerRetrofitService.create(url).postLog(json)
        var success: Boolean = false
        var error: String = ""
        call.enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                success = true
                Log.d(MainActivity.TAG, "success() " + response)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                success = false
                error = t.toString()
                Log.d(MainActivity.TAG, "success() " + t.toString())
            }
        }
        )
        if(success) {
            return Result.success()
        } else if(error.contains("code=500")) {
            return Result.retry()
        }
        return Result.failure()

    }

}