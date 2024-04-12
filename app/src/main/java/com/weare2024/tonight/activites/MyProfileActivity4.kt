package com.weare2024.tonight.activites

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile4Binding

class MyProfileActivity4 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile4Binding.inflate(layoutInflater) }
    var jj = ""

    val ccc = grayColor()
    val aaa = bonColor()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        overridePendingTransition(R.anim.from_right_enter_xml, R.anim.from_left_enter_xml)


        binding.btnBusan.setOnClickListener { click() }
        binding.btnDegu.setOnClickListener { click1() }
        binding.btnDegen.setOnClickListener { click2() }
        binding.btnInchen.setOnClickListener { click3() }
        binding.btnGwangju.setOnClickListener { click4() }
        binding.btnJeju.setOnClickListener { click5() }
        binding.btnSejong.setOnClickListener { click6() }
        binding.btnSeoul.setOnClickListener { click7() }
        binding.btnUlsan.setOnClickListener { click8() }



        binding.btnNext4.setOnClickListener {

            if (jj == "") {
                Toast.makeText(this, "지역을 선택 해주세요", Toast.LENGTH_SHORT).show()
            }else {
                if ((intent != null && intent.hasExtra("login_type"))) {
                    when (intent.getStringExtra("login_type")) {
                        "kakao" ->{
                            val uid = intent.getStringExtra("kakao_uid")
                            val nickname = intent.getStringExtra("nickname")
                            val gender = intent.getStringExtra("gender")
                            val height = intent.getStringExtra("height")
                            val year = intent.getIntExtra("year", 0)
                            val month = intent.getIntExtra("month", 1)
                            val day = intent.getIntExtra("day", 2)
                            val intent = Intent(this, MyProfileActivity5::class.java)

                            intent.putExtra("kakao_uid", uid)
                            intent.putExtra("nickname", nickname)
                            intent.putExtra("gender", gender)
                            intent.putExtra("height", height)
                            intent.putExtra("year", year)
                            intent.putExtra("month", month)
                            intent.putExtra("day", day)
                            intent.putExtra("jj", jj)
                            intent.putExtra("login_type", "kakao")
                            Toast.makeText(
                                this,
                                "$uid,$nickname,$gender,$height,$year,$month,$day,$jj",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(intent)
                        }
                        "naver"->{

                        }
                        "google"->{
                            val googleEmail = intent.getStringExtra("google_email")
                            val uid = intent.getStringExtra("google_uid")
                            val nickname = intent.getStringExtra("nickname")
                            val gender = intent.getStringExtra("gender")
                            val height = intent.getStringExtra("height")
                            val year = intent.getIntExtra("year", 0)
                            val month = intent.getIntExtra("month", 1)
                            val day = intent.getIntExtra("day", 2)
                            val intent = Intent(this, MyProfileActivity5::class.java)

                            intent.putExtra("google_uid", uid)
                            intent.putExtra("nickname", nickname)
                            intent.putExtra("gender", gender)
                            intent.putExtra("height", height)
                            intent.putExtra("year", year)
                            intent.putExtra("month", month)
                            intent.putExtra("day", day)
                            intent.putExtra("jj", jj)
                            intent.putExtra("login_type", "google")
                            intent.putExtra("google_email",googleEmail)
                            Toast.makeText(
                                this,
                                "$uid,$nickname,$gender,$height,$year,$month,$day,$jj",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

//    private fun clickNext() {
//
//
//
//
//
//
//    }

    private fun click() {
        binding.btnBusan.setBackgroundColor(ccc)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(aaa)
        jj = "부산"
    }

    private fun click1() {
        jj = binding.btnDegu.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(ccc)

        jj = "대구"


    }

    private fun click2() {
        jj = binding.btnDegen.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(ccc)
        binding.btnDegu.setBackgroundColor(aaa)

        jj = "대전"


    }

    private fun click3() {
        jj = binding.btnInchen.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(ccc)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(aaa)

        jj = "인천"


    }

    private fun click4() {
        jj = binding.btnGwangju.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(ccc)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(aaa)

        jj = "광주"


    }

    private fun click5() {
        jj = binding.btnJeju.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(ccc)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(aaa)

        jj = "제주"


    }

    private fun click6() {
        jj = binding.btnSejong.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(ccc)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(aaa)

        jj = "세종"


    }

    private fun click7() {
        jj = binding.btnSeoul.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(aaa)
        binding.btnSeoul.setBackgroundColor(ccc)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(aaa)

        jj = "서울"


    }

    private fun click8() {
        jj = binding.btnUlsan.text.toString()
        binding.btnBusan.setBackgroundColor(aaa)
        binding.btnUlsan.setBackgroundColor(ccc)
        binding.btnSeoul.setBackgroundColor(aaa)
        binding.btnJeju.setBackgroundColor(aaa)
        binding.btnSejong.setBackgroundColor(aaa)
        binding.btnGwangju.setBackgroundColor(aaa)
        binding.btnInchen.setBackgroundColor(aaa)
        binding.btnDegen.setBackgroundColor(aaa)
        binding.btnDegu.setBackgroundColor(aaa)

        jj = "울산"


    }

    fun grayColor(): Int {
        val r = 170
        val g = 170
        val b = 170
        return Color.rgb(r, g, b)
    }

    fun bonColor(): Int {

        val rr = 144
        val gg = 215
        val bb = 253
        return Color.rgb(rr, gg, bb)
    }
}