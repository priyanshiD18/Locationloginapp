//package com.example.locationloginapp.ui.view
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.lifecycle.lifecycleScope
//import com.example.locationloginapp.BaseActivity
//import com.example.locationloginapp.R
//import com.example.locationloginapp.data.repository.SessionRepository
//import com.example.locationloginapp.service.LocationService
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.flow.collectLatest
//
//class DashboardActivity : BaseActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_dashboard)
//        startService(Intent(this, LocationService::class.java))
//        observeLoginState()
//    }
//    private fun observeLoginState() {
//        lifecycleScope.launch {
//            SessionRepository.isLoggedIn.collectLatest{ isLoggedIn ->
//                if (!isLoggedIn) {
//                    Toast.makeText(
//                        this@DashboardActivity,
//                        "You moved out of permissible area",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }
//    }
////    override fun onStart() {
////        super.onStart()
////        if(!SessionRepository.isLoggedIn){
////            startActivity(Intent(this, LoginActivity::class.java))
////            finish()
////        }
////    }
//
//
//    override fun onDestroy() {
//        stopService(Intent(this, LocationService::class.java))
//        super.onDestroy()
//    }
//}

package com.example.locationloginapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.locationloginapp.BaseActivity
import com.example.locationloginapp.R
import com.example.locationloginapp.data.repository.SessionRepository
import com.example.locationloginapp.service.LocationService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DashboardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Start location service
        startService(Intent(this, LocationService::class.java))

        // Observe the login state
        lifecycleScope.launch {
            SessionRepository.isLoggedInFlow.collectLatest { isLoggedIn ->
                if (!isLoggedIn) {
                    Toast.makeText(
                        this@DashboardActivity,
                        "You are not in the correct location.",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, LocationService::class.java))
        super.onDestroy()
    }
}
