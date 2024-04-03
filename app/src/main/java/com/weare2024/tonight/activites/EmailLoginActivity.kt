package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityEmailLoginBinding
import com.weare2024.tonight.firebase.FBAuth

class EmailLoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnLogin.setOnClickListener { clickLogin() }
    }

    private fun clickLogin() {
        val email = binding.inputLayoutEmail.editText!!.text.toString()
        val password = binding.inputLayoutPassword.editText!!.text.toString()

        FBAuth.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}