package com.weare2024.tonight.activites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityBoardDetailBinding

class BoardDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBoardDetailBinding.inflate(layoutInflater) }
    private val bs: View by lazy { binding.bs }
    private val bsb: BottomSheetBehavior<View> by lazy { BottomSheetBehavior.from(bs) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.request.setOnClickListener { clickRequest() }
        binding.chat.setOnClickListener { clickChat() }
        binding.rl.background = null
    }
    private fun clickRequest(){
        if (bsb.state == BottomSheetBehavior.STATE_COLLAPSED)  // 상태 확인
            bsb.state = BottomSheetBehavior.STATE_EXPANDED // 시트 열기
        else {bsb.state = BottomSheetBehavior.STATE_COLLAPSED}  // 시트 닫기
    }
    private fun clickChat(){
        Toast.makeText(this, "채팅 채널로 연결 됩니다.", Toast.LENGTH_SHORT).show()
    }
}