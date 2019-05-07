package com.nickjgski.hw5

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.location.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.coroutines.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://locationcameracanvas.firebaseio.com/")
    private var ref: DatabaseReference = database.reference

    private var coroutineJob: Job? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationList: RecyclerView

    private val locationRequest = LocationRequest.create()?.apply {
        interval = 1000
        fastestInterval = 500
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    var latlong: Location? = null

    lateinit var  geocoder : Geocoder
    var addresses: List<Address> = emptyList()

    var query: Query = ref.child("loctimes")

    var options: FirebaseRecyclerOptions<LocationTime> =
        FirebaseRecyclerOptions.Builder<LocationTime>()
            .setQuery(query, LocationTime::class.java)
            .build()

    private var adapter = LocTimeAdapter(options)

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        geocoder = Geocoder(this, Locale.getDefault())

        locationList = findViewById(R.id.locations)
        locationList.layoutManager = LinearLayoutManager(this)
        locationList.adapter = adapter

        val fab: View = findViewById(R.id.add)
        fab.setOnClickListener {
            Log.d("Location", latlong.toString())
            latlong?.let {  printAddressForLocation(latlong!!) }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                location?.let{latlong=it}

            }

        //setting up the callback
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                latlong?.let { latlong = it }
            }
        }


        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            null)

    }

    fun getAddress(location: Location): String {
        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                // In this sample, we get just a single address.
                1)
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            return "Error: Service Not Available --$ioException"
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            return "Error: Invalid lat long used--$illegalArgumentException"
        }
        if(addresses.isEmpty())
            return "No address found :("
        return addresses[0].getAddressLine(0)
    }

    @SuppressLint("SimpleDateFormat")
    fun printAddressForLocation(location: Location){
        coroutineJob?.cancel()
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            val addressDeffered = async {
                getAddress(location)
            }
            val result = addressDeffered.await()
            Log.d("Location", result)
            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss")
            val format = simpleDateFormat.format(Date())
            writeNewLocTime(format, location.latitude.toString(), location.longitude.toString(), result, format)
        }

    }

    private fun writeNewLocTime(ltID: String, latitude: String, longitude: String, address: String, timestamp: String) {
        val locTime = LocationTime(latitude, longitude, address, timestamp)
        ref.child("loctimes").child(ltID).setValue(locTime)
        Log.d("Database", "location added")
    }

    inner class LocTimeAdapter(options: FirebaseRecyclerOptions<LocationTime>): FirebaseRecyclerAdapter<LocationTime, LocTimeAdapter.LocTimeHolder>(options) {

        override fun onBindViewHolder(holder: LocTimeHolder, position: Int, loctime: LocationTime) {

            holder.view.findViewById<TextView>(R.id.title).text = "<${loctime.latitude}, ${loctime.longitude}>"
            holder.view.findViewById<TextView>(R.id.addressText).text = "${loctime.address}"
            holder.view.findViewById<TextView>(R.id.timestamp).text = "${loctime.time}"
            holder.view.setOnClickListener {
                var gmmIntentUri = Uri.parse("geo:0,0?q=${loctime.latitude},${loctime.longitude}label(Captured here)")
                var mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
            holder.view.findViewById<Button>(R.id.delete).setOnClickListener {
                ref.child("loctimes").child("${loctime.time}").removeValue()
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocTimeHolder {

            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
            return LocTimeHolder(v)
        }

        inner class LocTimeHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){

            }
        }
    }

}
