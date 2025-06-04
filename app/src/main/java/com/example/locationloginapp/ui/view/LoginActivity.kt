package com.example.locationloginapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.locationloginapp.BaseActivity
import com.example.locationloginapp.R
import com.example.locationloginapp.ui.viewmodel.LoginViewModel
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.locationloginapp.data.repository.SessionRepository
import kotlinx.coroutines.flow.collectLatest

class LoginActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (!hasLocationPermissions()) {
            requestLocationPermissions(1002)
            return
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            viewModel.login()
            startActivity(Intent(this, DashboardActivity::class.java))
        }

    }
}
