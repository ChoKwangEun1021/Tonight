package com.weare2024.tonight.activites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityBoardDetailBinding

class BoardDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBoardDetailBinding.inflate(layoutInflater) }
    private val bs: View by lazy { binding.bs } //댓글 바텀시트
//    private val reportBs: View by lazy { R.layout.more112 } // 신고 바텀시트
    private val bsb: BottomSheetBehavior<View> by lazy { BottomSheetBehavior.from(bs) }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.request.setOnClickListener { clickComment() }
        binding.chat.setOnClickListener { clickChat() }
        binding.rl.background = null
        binding.toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener{
            @SuppressLint("SuspiciousIndentation")
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item!!.itemId == R.id.more112) {
                    showBottomSheet()
                    val singo1 = findViewById<TextView>(R.id.singo_1)
                        singo1?.setOnClickListener {
                            Toast.makeText(this@BoardDetailActivity, "${singo1.text}", Toast.LENGTH_SHORT).show()

                    }
//                    val singo2:TextView = findViewById(R.id.singo_2)
//                    singo2.setOnClickListener {
//
//                    }
//                    val singo3:TextView = findViewById(R.id.singo_3)
//                    singo3.setOnClickListener {
//
//                    }
//                    val singo4:TextView = findViewById(R.id.singo_4)
//                    singo4.setOnClickListener {
//
//                    }
//                    val singo5:TextView = findViewById(R.id.singo_5)
//                    singo5.setOnClickListener {
//
//                    }
                }
//                when(item!!.itemId){
//                    R.id.more112->{
//                        showBottomSheet()
//                    }
//                }
                return true
            }
        })

    }
    private fun showBottomSheet(){
        val dailog = BottomSheetDialog(this@BoardDetailActivity)
        val view = layoutInflater.inflate(R.layout.more112,null)
        dailog.setContentView(view)
        dailog.show()
    }
    private fun clickComment(){
        if (bsb.state == BottomSheetBehavior.STATE_COLLAPSED)  // 상태 확인
            bsb.state = BottomSheetBehavior.STATE_EXPANDED // 시트 열기
        else {bsb.state = BottomSheetBehavior.STATE_COLLAPSED}  // 시트 닫기
    }
    private fun clickChat(){
        Toast.makeText(this, "채팅 채널로 연결 됩니다.", Toast.LENGTH_SHORT).show()
    }
}