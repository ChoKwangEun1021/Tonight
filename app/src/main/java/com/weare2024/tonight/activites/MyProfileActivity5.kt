package com.weare2024.tonight.activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile5Binding

class MyProfileActivity5 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile5Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        overridePendingTransition(R.anim.from_right_enter_xml,R.anim.from_left_enter_xml)
//
//        binding.btnArbeit.setOnClickListener { click() }
//        binding.btnCeo.setOnClickListener { click1() }
//        binding.btnCompany.setOnClickListener { click2() }
//        binding.btnDotor.setOnClickListener { click3() }
//        binding.btnFinance.setOnClickListener { click4() }
//        binding.btnCategoryOther.setOnClickListener { click5() }
//        binding.btnFreelancer.setOnClickListener { click6() }
//        binding.btnJobSeeker.setOnClickListener { click7() }
//        binding.btnProfession.setOnClickListener { click8() }

    }

    private fun click(){




    }

}