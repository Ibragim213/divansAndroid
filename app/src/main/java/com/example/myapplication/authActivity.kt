package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.data.remote.RetrofitClient
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация репозитория
        authRepository = AuthRepository(RetrofitClient.apiService)

        // Переход на регистрацию
        binding.lingToRed.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Авторизация
        binding.button2Auth.setOnClickListener {
            val email = binding.userLoginAuth.text.toString()
            val password = binding.userPassAuth.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authRepository.login(
                email = email,
                password = password,
                onSuccess = { token ->
                    Toast.makeText(this, "Вход успешен!", Toast.LENGTH_SHORT).show()

                    // Переход на главный экран после успешного входа
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("token", token)
                    startActivity(intent)
                    finish()
                },
                onError = { error ->
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}