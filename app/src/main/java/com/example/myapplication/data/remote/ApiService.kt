package com.example.myapplication.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("/api/auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>
}

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String
)