package com.weare2024.tonight.activites

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile1Binding

class MyProfileActivity1 : AppCompatActivity() {

    var gendera = ""
    private val binding by lazy { ActivityMyProfile1Binding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val man = findViewById<RadioButton>(R.id.man)
        val female = findViewById<RadioButton>(R.id.female)
        binding.btnNext1.setOnClickListener { clickNext() }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.man -> {
                    gendera = "남자"
                    female.isChecked = false
                }

                R.id.female -> {
                    gendera = "여자"
                    man.isChecked = false
                }
            }
        }



        overridePendingTransition(R.anim.from_right_enter_xml, R.anim.from_left_enter_xml)
        val imguri = intent.getStringExtra("profileImgUri")
        Log.d("pppp","$imguri")


    }

    fun clickNext() {

        if (intent != null && intent.hasExtra("login_type")){
            when(intent.getStringExtra("login_type")) {
                "kakao" ->{
                    if (gendera == "남자") {
                        val uid = intent.getStringExtra("kakao_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "남자"
                        intent.putExtra("gender", gender)
                        intent.putExtra("kakao_uid", uid)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("profileImgUri",imguri)
                        intent.putExtra("login_type", "kakao")
                        startActivity(intent)


                    } else if (gendera == "여자") {
                        val uid = intent.getStringExtra("kakao_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "여자"
                        intent.putExtra("gender", gender)
                        intent.putExtra("kakao_uid", uid)
                        intent.putExtra("profileImgUri",imguri)
                        intent.putExtra("login_type", "kakao")
                        intent.putExtra("nickname", nickname)

                        startActivity(intent)
                    } else Toast.makeText(this, "성별을 선택해 주세요", Toast.LENGTH_SHORT).show()
                }
                "naver" ->{
                    if (gendera == "남자") {
                        val uid = intent.getStringExtra("naver_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val email = intent.getStringExtra("naver_email")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "남자"

                        intent.putExtra("gender", gender)
                        intent.putExtra("naver_uid", uid)
                        intent.putExtra("naver_email", email)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("login_type", "naver")
                        startActivity(intent)

                    } else if (gendera == "여자") {
                        val uid = intent.getStringExtra("naver_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val email = intent.getStringExtra("email")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "여자"

                        intent.putExtra("gender", gender)
                        intent.putExtra("naver_uid", uid)
                        intent.putExtra("email", email)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("login_type", "naver")
                        startActivity(intent)

                    } else Toast.makeText(this, "성별을 선택해 주세요", Toast.LENGTH_SHORT).show()

                }

                "google" ->{
                    if (gendera == "남자") {
                        val googleEmail = intent.getStringExtra("google_email")
                        val uid = intent.getStringExtra("google_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "남자"
                        intent.putExtra("gender", gender)
                        intent.putExtra("google_uid", uid)
                        intent.putExtra("login_type", "google")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("google_email",googleEmail)
                        startActivity(intent)

                    } else if (gendera == "여자") {
                        val googleEmail = intent.getStringExtra("google_email")
                        val uid = intent.getStringExtra("google_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "여자"
                        intent.putExtra("gender", gender)
                        intent.putExtra("google_uid", uid)
                        intent.putExtra("login_type", "google")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("google_email",googleEmail)
                        startActivity(intent)
                    } else Toast.makeText(this, "성별을 선택해 주세요", Toast.LENGTH_SHORT).show()
                }

                "email" -> {
                    if (gendera == "남자") {
                        val email = intent.getStringExtra("email")
                        val uid = intent.getStringExtra("email_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val password = intent.getStringExtra("password")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "남자"
                        intent.putExtra("gender", gender)
                        intent.putExtra("email_uid", uid)
                        intent.putExtra("login_type", "email")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("email",email)
                        intent.putExtra("password",password)
                        startActivity(intent)
//                        Toast.makeText(this, "$email $password $nickname $uid $gender", Toast.LENGTH_SHORT).show()
                        AlertDialog.Builder(this).setMessage("$email $password $nickname $uid $gender")

                    } else if (gendera == "여자") {
                        val email = intent.getStringExtra("email")
                        val uid = intent.getStringExtra("email_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val password = intent.getStringExtra("password")
                        val intent = Intent(this, MyProfileActivity2::class.java)
                        val gender = "여자"
                        intent.putExtra("gender", gender)
                        intent.putExtra("email_uid", uid)
                        intent.putExtra("login_type", "email")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("email",email)
                        intent.putExtra("password",password)
                        startActivity(intent)

                        Toast.makeText(this, "$email $password $nickname $uid $gender", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this, "성별을 선택해 주세요", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }
}
