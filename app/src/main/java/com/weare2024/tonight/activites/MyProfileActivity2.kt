package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.Toast
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

        overridePendingTransition(R.anim.from_right_enter_xml,R.anim.from_left_enter_xml)
        var numberPicker = binding.number

        numberPicker.minValue = 130
        numberPicker.maxValue= 250

        numberPicker.wrapSelectorWheel = false
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.btnNext2.setOnClickListener {
            val intent = Intent(this,MyProfileActivity3::class.java)

            val selectedNumber = numberPicker.value
//            Toast.makeText(this, "선택된 숫자"+selectedNumber, Toast.LENGTH_SHORT).show()


            val uid = intent.getStringExtra("kakao_uid")
            val nickname = intent.getStringExtra("nickname")
            val gender = intent.getStringExtra("gender")
            val height = "${selectedNumber}cm"

            intent.putExtra("kakao_uid",uid)
            intent.putExtra("nickname",nickname)
            intent.putExtra("gender",gender)
            intent.putExtra("height",height)

            startActivity(intent)
        }






    }
}