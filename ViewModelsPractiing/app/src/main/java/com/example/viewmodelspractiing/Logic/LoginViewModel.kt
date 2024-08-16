package com.example.viewmodelspractiing.Logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.IDLE)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.LOADING

            // Simulating a network request or authentication process
            delay(2000)

            // Assuming successful login for demonstration
            if (username == "user" && password == "password") {
                _loginState.value = LoginState.SUCCESS
            } else {
                _loginState.value = LoginState.ERROR("Invalid credentials")
            }
        }
    }
}
sealed class LoginState {
    object IDLE : LoginState()
    object LOADING : LoginState()
    object SUCCESS : LoginState()
    data class ERROR(val message: String) : LoginState()
}