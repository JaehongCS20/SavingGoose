package com.example.pj4test

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.pj4test.fragment.CameraFragment
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    // permissions
    private val permissions = arrayOf(RECORD_AUDIO, CAMERA)
    private val PERMISSIONS_REQUEST = 0x0000001;

    private val manager = supportFragmentManager
    private var camera = manager.findFragmentById(R.id.cameraFragmentContainerView)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        camera = manager.findFragmentById(R.id.cameraFragmentContainerView)
        checkPermissions() // check permissions
    }

    private fun checkPermissions() {
        if (permissions.all{ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED}){
            Log.d(TAG, "All Permission Granted")
        }
        else{
            requestPermissions(permissions, PERMISSIONS_REQUEST)
        }
    }

    public fun addCamera() {
        val fragment = camera
        if(fragment != null) {
            (fragment as CameraFragment?)!!.setUpCamera()
            Log.d(TAG, "Camera Start")
        }
    }

    public fun deleteCamera() {
        val fragment = camera
        if(fragment != null) {
            (fragment as CameraFragment?)!!.unSetCamera()
            Log.d(TAG, "Camera End")
        }
    }
}