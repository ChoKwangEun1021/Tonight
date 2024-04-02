package com.weare2024.tonight.activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityBoradDetailBinding

class BoradDetail : AppCompatActivity() {
    private val binding by lazy { ActivityBoradDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.request.setOnClickListener { clickRequest() }
        binding.chat.setOnClickListener { clickChat() }
    }
    private fun clickRequest(){

    }
    private fun clickChat(){

    }
}