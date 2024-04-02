package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.weare2024.tonight.databinding.ActivityBoardDetailBinding

class BoardDetail : AppCompatActivity() {
    private val binding by lazy { ActivityBoardDetailBinding.inflate(layoutInflater) }

    val bs = binding.bs
    val bsb = BottomSheetBehavior.from(bs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.request.setOnClickListener { clickRequest() }
        binding.chat.setOnClickListener { clickChat() }
    }
    private fun clickRequest(){
        bsb.state = BottomSheetBehavior.STATE_EXPANDED

    }
    private fun clickChat(){
        startActivity(Intent(this,ChatingActivity::class.java))
    }
}