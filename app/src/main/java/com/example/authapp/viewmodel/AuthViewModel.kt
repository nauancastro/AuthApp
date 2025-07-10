package com.example.authapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authapp.repository.AuthRepository
import com.example.authapp.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: UserOut) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val resp = repository.register(UserCreate(username, email, password))
            if (resp.isSuccessful) _authState.value = AuthState.Success(resp.body()!!)
            else _authState.value = AuthState.Error("Erro no registro")
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val resp = repository.login(LoginRequest(email, password))
            if (resp.isSuccessful) {
                val token = resp.body()!!.token
                repository.saveToken(token)
                _authState.value = AuthState.Success(UserOut(0, "", ""))
            } else _authState.value = AuthState.Error("Credenciais inválidas")
        }
    }

    fun checkAuth() {
        viewModelScope.launch {
            repository.getToken().firstOrNull()?.let { token ->
                if (token != null) {
                    val resp = repository.me(token)
                    if (resp.isSuccessful) _authState.value = AuthState.Success(resp.body()!!)
                    else _authState.value = AuthState.Idle
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _authState.value = AuthState.Idle
        }
    }
}