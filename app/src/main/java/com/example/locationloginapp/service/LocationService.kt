package com.example.locationloginapp.service

import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.locationloginapp.data.repository.SessionRepository

class LocationService : Service() {

    private lateinit var locationManager: LocationManager

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val officeLat = 35.421998333333335
            val officeLon = -122.084

            val distance = floatArrayOf(0f)

            // Log current device location
            Log.d("LocationService", "Current Location: ${location.latitude}, ${location.longitude}")

            // Calculate distance from office
            Location.distanceBetween(
                location.latitude, location.longitude,
                officeLat, officeLon, distance
            )

            // Log the calculated distance
            Log.d("LocationService", "Distance from office: ${distance[0]} meters")

            // Check proximity and log in/out accordingly
            if (distance[0] <= 80f) {
                if (!SessionRepository.isLoggedIn) {
                    SessionRepository.login()
                    Log.d("LocationService", "User logged in (inside office)")
                } else {
                    Log.d("LocationService", "User is already logged in (inside office)")
                }
            } else {
                if (SessionRepository.isLoggedIn) {
                    SessionRepository.logout()
                    Log.d("LocationService", "User logged out (outside office)")
                } else {
                    SessionRepository.logout()
                    Log.d("LocationService", "User is already logged out (outside office)")
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000L, // Update every 10 seconds
                0f, // No minimum distance change
                locationListener,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(locationListener)
        Log.d("LocationService", "Location updates stopped")
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
