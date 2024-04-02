package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), OnClickListener {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnGo.setOnClickListener(this)
        binding.btnLoginKakao.setOnClickListener(this)
        binding.btnLoginNaver.setOnClickListener(this)
        binding.btnLoginGoogle.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignup.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_go -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.btn_login_kakao -> {}
            R.id.btn_login_naver -> {}
            R.id.btn_login_google -> {}
            R.id.btn_login -> {
                startActivity(Intent(this, EmailLoginActivity::class.java))
            }
            R.id.btn_signup -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }
        }
    }
}