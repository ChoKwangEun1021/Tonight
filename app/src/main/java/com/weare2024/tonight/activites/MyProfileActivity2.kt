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

        overridePendingTransition(R.anim.from_right_enter_xml, R.anim.from_left_enter_xml)
        var numberPicker = binding.number

        numberPicker.minValue = 130
        numberPicker.maxValue = 250

        numberPicker.wrapSelectorWheel = false
        numberPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.btnNext2.setOnClickListener {

            val selectedNumber = numberPicker.value
//            Toast.makeText(this, "선택된 숫자"+selectedNumber, Toast.LENGTH_SHORT).show()
            if ((intent != null && intent.hasExtra("login_type"))) {
                when (intent.getStringExtra("login_type")) {
                    "kakao" -> {
                        val uid = intent.getStringExtra("kakao_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val gender = intent.getStringExtra("gender")
                        val intent = Intent(this, MyProfileActivity3::class.java)
                        val height = "${selectedNumber}cm"

                        intent.putExtra("kakao_uid", uid)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("gender", gender)
                        intent.putExtra("height", height)
                        intent.putExtra("login_type", "kakao")
                        Toast.makeText(
                            this,
                            "$uid,$nickname,$gender,$height",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                    }

                    "naver" -> {
                        val uid = intent.getStringExtra("naver_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val gender = intent.getStringExtra("gender")
                        val email = intent.getStringExtra("email")
                        val intent = Intent(this,MyProfileActivity3::class.java)
                        val height = "${selectedNumber}cm"

                        intent.putExtra("naver_uid",uid)
                        intent.putExtra("email", email)
                        intent.putExtra("nickname",nickname)
                        intent.putExtra("gender",gender)
                        intent.putExtra("height",height)
                        intent.putExtra("login_type", "naver")
                        Toast.makeText(this, "$uid,$nickname,$gender,$height", Toast.LENGTH_SHORT).show()

                        startActivity(intent)
                    }

                    "google" -> {
                        val googleEmail = intent.getStringExtra("google_email")
                        val uid = intent.getStringExtra("google_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val gender = intent.getStringExtra("gender")
                        val intent = Intent(this, MyProfileActivity3::class.java)
                        val height = "${selectedNumber}cm"

                        intent.putExtra("google_email", googleEmail)
                        intent.putExtra("google_uid", uid)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("gender", gender)
                        intent.putExtra("height", height)
                        intent.putExtra("login_type", "google")
                        Toast.makeText(
                            this,
                            "$uid,$nickname,$gender,$height",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(intent)
                    }
                }
            }
        }
    }
}