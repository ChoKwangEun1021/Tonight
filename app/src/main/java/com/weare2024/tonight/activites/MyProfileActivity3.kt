package com.weare2024.tonight.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.databinding.ActivityMyProfile3Binding

class MyProfileActivity3 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile3Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



    }
}