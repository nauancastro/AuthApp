package com.example.authapp.repository

import android.content.Context
import com.example.authapp.remote.AuthApi
import com.example.authapp.model.*
import com.example.authapp.util.TokenManager

class AuthRepository(private val api: AuthApi, context: Context) {
    private val tokenManager = TokenManager(context)

    suspend fun register(userCreate: UserCreate) = api.register(userCreate)
    suspend fun login(loginRequest: LoginRequest) = api.login(loginRequest)
    suspend fun saveToken(token: String) = tokenManager.saveToken(token)
    fun getToken() = tokenManager.tokenFlow
    suspend fun logout() = tokenManager.clearToken()
    suspend fun me(token: String) = api.me("Bearer $token")
}