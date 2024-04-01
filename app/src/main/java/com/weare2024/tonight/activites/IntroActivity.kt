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
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(R.drawable.cat2).into(binding.ivLogo)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

    }
}