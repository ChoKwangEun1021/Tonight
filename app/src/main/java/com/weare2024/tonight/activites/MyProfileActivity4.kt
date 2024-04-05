package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile4Binding

class MyProfileActivity4 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile4Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnNext4.setOnClickListener { startActivity(Intent(Intent(this,MyProfileActivity5::class.java)) )}
        overridePendingTransition(R.anim.from_right_enter_xml,R.anim.from_left_enter_xml)
    }
}