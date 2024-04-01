package com.weare2024.tonight.activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMainBinding
import com.weare2024.tonight.fragment.ChatFragment
import com.weare2024.tonight.fragment.HomeFragment
import com.weare2024.tonight.fragment.ListFragment
import com.weare2024.tonight.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.container, HomeFragment()).commit()

        binding.bnv.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_bnv_home -> supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
                R.id.menu_bnv_list -> supportFragmentManager.beginTransaction().replace(R.id.container, ListFragment()).commit()
                R.id.menu_bnv_chat -> supportFragmentManager.beginTransaction().replace(R.id.container, ChatFragment()).commit()
                R.id.menu_bnv_profile -> supportFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment()).commit()
            }
            true
        }

    }
}