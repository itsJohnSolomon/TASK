package com.jojo.cloudintask.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jojo.cloudintask.R
import com.jojo.cloudintask.databinding.ActivityMainBinding
import com.jojo.cloudintask.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private val CALL_PHONE_PERMISSION_REQUEST_CODE = 1
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // Permission has been granted, start the main activity
            Handler().postDelayed({
                startMainActivity()
            }, 4000)

        } else {
            // Permission has not been granted, request it
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_PERMISSION_REQUEST_CODE)
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Check if the CALL_PHONE permission has been granted
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission has been granted, start the main activity
            startMainActivity()
        } else {
            // Permission has not been granted, show a message and close the app
            Toast.makeText(this, "You need to grant the CALL_PHONE permission to use this app.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}