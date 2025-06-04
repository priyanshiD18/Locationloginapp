package com.example.locationloginapp.data.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
object SessionRepository {

    private val _isLoggedInFlow = MutableStateFlow(true)
    val isLoggedInFlow = _isLoggedInFlow.asStateFlow()

    var isLoggedIn: Boolean
        get() = _isLoggedInFlow.value
        private set(value) {
            _isLoggedInFlow.value = value
        }

    fun login() {
        isLoggedIn = true
    }

    fun logout() {
        isLoggedIn = false
    }
}