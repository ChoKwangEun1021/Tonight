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

        val nickname = intent.getStringExtra("nickname")
        val gender = intent.getStringExtra("gender")
        val height = intent.getStringExtra("height")


        var datePicker: DatePicker = binding.date

        overridePendingTransition(R.anim.from_right_enter_xml, R.anim.from_left_enter_xml)

        binding.btnNext3.setOnClickListener {
            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth

//            if (year == null || month == null || day == null) {
//                Toast.makeText(this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show()
//            } else {
            if ((intent != null && intent.hasExtra("login_type"))) {
                when (intent.getStringExtra("login_type")) {
                    "kakao" -> {
                        val uid = intent.getStringExtra("kakao_uid")
                        val intent = Intent(this, MyProfileActivity4::class.java)
                        intent.putExtra("kakao_uid", uid)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("gender", gender)
                        intent.putExtra("height", height)
                        intent.putExtra("year", year)
                        intent.putExtra("month", month)
                        intent.putExtra("day", day)
                        intent.putExtra("login_type", "kakao")
                        Toast.makeText(
                            this,
                            "$uid,$nickname,$gender,$height.$year.$month.$day",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)

                    }

                    "naver" -> {

                        val uid = intent.getStringExtra("naver_uid")
                        val email = intent.getStringExtra("naver_email")
                        val intent = Intent(this, MyProfileActivity4::class.java)

                        intent.putExtra("naver_uid", uid)
                        intent.putExtra("naver_email", email)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("gender", gender)
                        intent.putExtra("height", height)
                        intent.putExtra("year", year)
                        intent.putExtra("month", month)
                        intent.putExtra("day", day)
                        intent.putExtra("login_type", "naver")

                        Toast.makeText(
                            this,
                            "$uid,$nickname,$gender,$height.$year.$month.$day",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                    }

                    "google" -> {
                        val uid = intent.getStringExtra("google_uid")
                        val googleEmail = intent.getStringExtra("google_email")
                        val intent = Intent(this, MyProfileActivity4::class.java)
                        intent.putExtra("google_uid", uid)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("gender", gender)
                        intent.putExtra("height", height)
                        intent.putExtra("year", year)
                        intent.putExtra("month", month)
                        intent.putExtra("day", day)
                        intent.putExtra("login_type", "google")
                        intent.putExtra("google_email",googleEmail)
                        Toast.makeText(
                            this,
                            "$uid,$nickname,$gender,$height.$year.$month.$day",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                    }
                }
            }
        }
    }
}



