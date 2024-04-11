package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile3Binding
import java.time.Year

class MyProfileActivity3 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile3Binding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val uid = intent.getStringExtra("kakao_uid")
        val nickname = intent.getStringExtra("nickname")
        val gender = intent.getStringExtra("gender")
        val height = intent.getStringExtra("height")
        val intent = Intent(this, MyProfileActivity4::class.java)

        var datePicker: DatePicker = binding.date

        overridePendingTransition(R.anim.from_right_enter_xml, R.anim.from_left_enter_xml)

        binding.btnNext3.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth

            if (year == null || month == null || day == null){
                Toast.makeText(this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{

                intent.putExtra("kakao_uid", uid)
                intent.putExtra("nickname", nickname)
                intent.putExtra("gender", gender)
                intent.putExtra("height", height)
                intent.putExtra("year", year)
                intent.putExtra("month", month)
                intent.putExtra("day", day)
                startActivity(intent)

            }


        }
    }
}


