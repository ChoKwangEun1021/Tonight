package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.descendants
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile2Binding

class MyProfileActivity2 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile2Binding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnNext2.setOnClickListener { startActivity(Intent(Intent(this,MyProfileActivity3::class.java))) }
        overridePendingTransition(R.anim.from_right_enter_xml,R.anim.from_left_enter_xml)
        var numberPicker = binding.number

        numberPicker = binding.number

        numberPicker.value = 165
        numberPicker.minValue = 130
        numberPicker.maxValue= 200

        numberPicker.wrapSelectorWheel = false
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

//        Log.e("text","${binding.btnNext2}")
//        numberPicker.setOnValueChangedListener{ picker, oldVal,newVal ->
//
//        }

        binding.btnNext2.setOnClickListener {
            val intent = Intent(this,MyProfileActivity3::class.java)
            intent.getStringExtra("numberpiker")
            Log.e("22222","$numberPicker")
            startActivity(intent)
        }


        val uid = intent.getStringExtra("uid")
        val nickname = intent.getStringExtra("nickName")
        val gender = intent.getStringExtra("gender")
        val height = "187cm"
        val intent = Intent(this,MyProfileActivity3::class.java)
        intent.putExtra("kakao_uid",uid)
        intent.putExtra("nickName",nickname)
        intent.putExtra("gender",gender)
        intent.putExtra("height",height)




    }
}