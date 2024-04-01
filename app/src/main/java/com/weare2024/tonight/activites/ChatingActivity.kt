package com.weare2024.tonight.activites

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.firebase.ChatData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatingBinding
    var room:String = "s"
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val msgItem:MutableList<ChatData> = mutableListOf()

        binding.toolvar.title = room
        binding.toolvar.subtitle = G.name
        binding.btnSend.setOnClickListener { btnSend() }

    }
    private fun btnSend(){
        val nickname = G.name
        val image = G.image
        val msg = binding.et.text.toString()
        val time =SimpleDateFormat("HH:mm", Locale.KOREA).format(Date())

        val item =ChatData(nickname,msg,time,image)

    }
}