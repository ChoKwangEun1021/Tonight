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
        val uid = intent.getStringExtra("uid")
        val nickname = intent.getStringExtra("nickName")
        val gender = intent.getStringExtra("gender")
        val height = intent.getStringExtra("height")

        var datePicker: DatePicker = binding.date

        overridePendingTransition(R.anim.from_right_enter_xml, R.anim.from_left_enter_xml)

        binding.btnNext3.setOnClickListener {
            val intent = Intent(this, MyProfileActivity4::class.java)
            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth
            intent.putExtra("yy", year)
            intent.putExtra("mm", month)
            intent.putExtra("dd", day)

            intent.putExtra("kakao_uid", uid)
            intent.putExtra("nickName", nickname)
            intent.putExtra("gender", gender)
            intent.putExtra("height", height)

            startActivity(intent)
//            Log.d("datapicker","$year $month $day")
        }
    }
}


