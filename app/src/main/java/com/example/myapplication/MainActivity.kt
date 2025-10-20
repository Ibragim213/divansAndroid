package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.data.remote.RetrofitClient
import com.example.myapplication.data.repository.AuthRepository
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация репозитория
        authRepository = AuthRepository(RetrofitClient.apiService)

        // Находим любой существующий элемент для перехода
        // Например, используем заголовок или создаем программно
        binding.mainLabel.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        // ИЛИ создаем программную кнопку перехода
        val switchToAuth = findViewById<android.widget.TextView>(R.id.button) // если у вас есть такой ID
        switchToAuth?.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        // Регистрация
        binding.button2.setOnClickListener {
            val email = binding.userEmail.text.toString()
            val password = binding.userPass.text.toString()
            val login = binding.userLogin.text.toString()

            if (email.isEmpty() || password.isEmpty() || login.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authRepository.register(
                email = email,
                password = password,
                firstName = login,
                onSuccess = { token ->
                    Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()

                    // Переход на главный экран после успешной регистрации
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