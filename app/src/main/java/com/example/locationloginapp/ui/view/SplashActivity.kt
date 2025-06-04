//// SplashActivity.kt
//package com.example.locationloginapp.ui.view
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import android.util.Log
//import com.example.locationloginapp.BaseActivity
//import com.example.locationloginapp.R
//import androidx.lifecycle.lifecycleScope
//import kotlinx.coroutines.flow.collectLatest
//import com.example.locationloginapp.data.repository.SessionRepository
//import com.example.locationloginapp.data.repository.SessionRepository.isLoggedIn
//
//class SplashActivity : BaseActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            if (hasLocationPermissions()) {
//                startActivity(Intent(this, LoginActivity::class.java))
//            } else {
//                requestLocationPermissions(1001)
//            }
//            finish()
//        }, 1500)
////        lifecycleScope.launchWhenStarted {
////            SessionRepository.isLoggedInFlow.collectLatest { isLoggedIn ->
////                if (!isLoggedIn) {
////                    Log.d("MainActivity", "User logged out — finishing activity")
////                    finish() // Close activity
////                }
////            }
////        }
//    }
//}

package com.example.locationloginapp.ui.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.locationloginapp.BaseActivity
import com.example.locationloginapp.R

class SplashActivity : BaseActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    private val FOREGROUND_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val BACKGROUND_PERMISSION = Manifest.permission.ACCESS_BACKGROUND_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (hasForegroundPermissions()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                    ContextCompat.checkSelfPermission(this, BACKGROUND_PERMISSION) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Ask for background location permission if needed
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(BACKGROUND_PERMISSION),
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                } else {
                    proceedToNextScreen()
                }
            } else {
                ActivityCompat.requestPermissions(this, FOREGROUND_PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
            }
        }, 1500)
    }

    private fun hasForegroundPermissions(): Boolean =
        FOREGROUND_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun proceedToNextScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (permissions.contains(BACKGROUND_PERMISSION)) {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    proceedToNextScreen()
                } else {
                    Toast.makeText(this, "Background location is required", Toast.LENGTH_LONG).show()
                    finish()
                }
            } else {
                // Foreground permissions result
                if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(BACKGROUND_PERMISSION),
                            LOCATION_PERMISSION_REQUEST_CODE
                        )
                    } else {
                        proceedToNextScreen()
                    }
                } else {
                    Toast.makeText(this, "Location permissions are required", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }
}
