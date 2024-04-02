package com.weare2024.tonight.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.databinding.ActivityMyProfile1Binding
import com.weare2024.tonight.databinding.ActivityMyProfileBirthdayBinding

class MyProfileActivity1 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}