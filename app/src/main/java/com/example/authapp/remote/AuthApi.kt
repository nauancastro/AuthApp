package com.example.authapp.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import com.example.authapp.model.UserCreate
import com.example.authapp.model.LoginRequest
import com.example.authapp.model.TokenResponse
import com.example.authapp.model.UserOut

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body userCreate: UserCreate): Response<UserOut>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<TokenResponse>

    @GET("auth/me")
    suspend fun me(@Header("Authorization") bearerToken: String): Response<UserOut>
}
