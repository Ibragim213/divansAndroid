package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.ApiService
import com.example.myapplication.data.remote.AuthResponse
import com.example.myapplication.data.remote.LoginRequest
import com.example.myapplication.data.remote.RegisterRequest
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class AuthRepository(private val apiService: ApiService) {

    fun login(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.login(LoginRequest(email, password)).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!.token)
                } else {
                    onError("Ошибка авторизации: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                onError("Ошибка сети: ${t.message}")
            }
        })
    }

    fun register(
        email: String,
        password: String,
        firstName: String?,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.register(RegisterRequest(email, password, firstName)).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!.token)
                } else {
                    onError("Ошибка регистрации: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                onError("Ошибка сети: ${t.message}")
            }
        })
    }
}