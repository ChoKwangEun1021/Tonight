package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile1Binding

class MyProfileActivity1 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnNext1.setOnClickListener { startActivity(Intent(Intent(this,MyProfileActivity2::class.java))) }
        overridePendingTransition(R.anim.from_right_enter_xml,R.anim.from_left_enter_xml)


    }
}