package com.nickjgski.touchcameratutorial

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.example.t07.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // an instance of MyCanvas
    lateinit var myCanvas:MyCanvas
    //an instance of TouchHandler that will be set-up to listen to touch events from within MyCanvas.
    lateinit var touchHandler: TouchHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//linking with the XML
        myCanvas = findViewById<MyCanvas>(R.id.myCanvas)
//instantiating a TouchHandler instance. Notice that we are passing a MainActivity instance.
        touchHandler = TouchHandler(this)
        myCanvas.setOnTouchListener(touchHandler)
    }
//touchandler-> this function relays it to -> myCanvas
//You can also allow touchHandler to update myCanvas directly(this will require passing myCanvas to touchHandler).
    fun addNewPath(id: Int, x: Float, y: Float) {
        myCanvas.addPath(id, x, y)
    }
    fun updatePath(id: Int, x: Float, y: Float) {
        myCanvas.updatePath(id, x, y)
    }
    fun removePath(id: Int) {
        myCanvas.removePath(id)
    }
    fun onDoubleTap(){
//double tapping changes the background to a random color
        myCanvas.setBackgroundColor(
            Color.rgb(
                (0..255).random(), (0..255).random(),
                (0..255).random()
            )
        )
    }
    fun onLongPress(){
//takes a picture and chnages the background to it
        dispatchTakePictureIntent()
    }
    ///////////taken from https://developer.android.com/training/camera/photobasics/
    lateinit var currentPhotoPath: String
    @Throws(IOException::class)
    private fun createImageFile(): File {
// Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
    ///////////taken from https://developer.android.com/training/camera/photobasics/
    val REQUEST_TAKE_PHOTO = 1
    lateinit var photoURI: Uri
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
// Error occurred while creating the File
                    null
                }
// Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.t07.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }
    ///////////taken from https://developer.android.com/training/camera/photobasics/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_TAKE_PHOTO && resultCode === Activity.RESULT_OK) {
            this.contentResolver.notifyChange(photoURI, null)
            val cr = this.contentResolver
            Log.d("test", "result ok")
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(cr, photoURI)
                myCanvas.background = BitmapDrawable(resources, bitmap)
            } catch (e: IOException) {
                Log.d("test", "activity result error")
                e.printStackTrace()
            }
        }
    }
}
