package com.example.locationloginapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationloginapp.data.repository.SessionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    fun login() {
        viewModelScope.launch {
            // Simulate login logic
            delay(1000)
            SessionRepository.login()
        }
    }
}