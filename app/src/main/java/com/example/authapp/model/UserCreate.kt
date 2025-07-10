package com.example.authapp.model

data class UserCreate(
    val username: String,
    val email: String,
    val password: String
)