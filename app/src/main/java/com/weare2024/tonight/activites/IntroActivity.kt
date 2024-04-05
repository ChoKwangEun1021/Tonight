package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spf = getSharedPreferences("userInfo", MODE_PRIVATE)
        G.uid = spf.getString("uid", "").toString()
        G.nickname = spf.getString("nickname", "").toString()

        Glide.with(this).load(R.drawable.cat2).into(binding.ivLogo)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = if (isLogin()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)

    }

    private fun isLogin() : Boolean {
        val spf = getSharedPreferences("loginSave" , MODE_PRIVATE)
        return spf.getBoolean("isLogin", false)
    }
}