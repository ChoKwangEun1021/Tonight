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

        var datePicker: DatePicker = binding.date

        binding.btnNext3.setOnClickListener { startActivity(Intent(Intent(this,MyProfileActivity4::class.java)) )}
        overridePendingTransition(R.anim.from_right_enter_xml,R.anim.from_left_enter_xml)


        binding.date.init(datePicker.year, datePicker.month, datePicker.dayOfMonth ){_,year,monthOfYear,dayOfMonth ->
            yy = year.toString()
            mm = (monthOfYear).toString()
            dd = dayOfMonth.toString()
            check = true
        }
        /*datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$year/${monthOfYear + 1} /$dayOfMonth"
            Toast.makeText(this, "선택한 날짜 :$selectedDate", Toast.LENGTH_SHORT).show()
        }*/
        binding.btnNext3.setOnClickListener {
            val intent = Intent(this,MyProfileActivity4::class.java)
            intent.putExtra("yy",yy)
            intent.putExtra("mm",mm)
            intent.putExtra("dd",dd)
            startActivity(intent)
            Log.d("datapicker","$datePicker")
        }



        val uid = intent.getStringExtra("uid")
        val nickname = intent.getStringExtra("nickName")
        val gender = intent.getStringExtra("gender")
        val height = intent.getStringExtra("height")
        val intent = Intent(this,MyProfileActivity4::class.java)
        intent.putExtra("kakao_uid",uid)
        intent.putExtra("nickName",nickname)
        intent.putExtra("gender",gender)
        intent.putExtra("height",height)

    }

    companion object{

        var yy:String? = null
        var mm:String? = null
        var dd:String? = null
        var check = false

    }
}