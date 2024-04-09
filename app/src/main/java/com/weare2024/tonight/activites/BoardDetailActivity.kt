package com.weare2024.tonight.activites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityBoardDetailBinding
import com.weare2024.tonight.network.RetrofitHelper
import com.weare2024.tonight.network.RetrofitService

class BoardDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBoardDetailBinding.inflate(layoutInflater) }
    private val bs: View by lazy { binding.bs } //댓글 바텀시트
    private val bsb: BottomSheetBehavior<View> by lazy { BottomSheetBehavior.from(bs) }
    private val rl_title : View by lazy { binding.rlTitle }
    private val itemList = mutableMapOf<String, String>()
    private var imgPath: String? = null
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.request.setOnClickListener { clickComment() }
        binding.chat.setOnClickListener { clickChat() }
//        binding.sendupload.setOnClickListener { clickSendUpload() }
        binding.rlTitle.setOnClickListener { clickTitle() }
//        itemList.add(SampleComment(R.drawable.profle,"잘생긴 오빠","누나 안녕하세요"))
//        itemList.add(SampleComment(R.drawable.profle,"존예녀 누나","오빠 안녕하세요"))
//        itemList.add(SampleComment(R.drawable.profle,"훈훈한 오빠","모두들 안녕"))
//        binding.recyclerComment.adapter!!.notifyDataSetChanged()
        binding.rl.background = null
        binding.toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item!!.itemId == R.id.more112) {
                    showBottomSheet()
                    }
                return true
            }
        })
    }
    private fun insertData() {
        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
        val retrofitService = retrofit.create(RetrofitService::class.java)

        itemList["uid"] = "uid"
        itemList["nickname"] = "nickname"
        itemList["comment"] = "comment"

    }

        private fun showBottomSheet(){
        val dailog = BottomSheetDialog(this@BoardDetailActivity)
        val view = layoutInflater.inflate(R.layout.more112,null)
        dailog.setContentView(view)
        val singo1 = view.findViewById<TextView>(R.id.singo_1)
        singo1.setOnClickListener {
            Toast.makeText(this@BoardDetailActivity, "신고가 접수 되었습니다", Toast.LENGTH_SHORT).show()
        }
        val singo2 = view.findViewById<TextView>(R.id.singo_2)
        singo2.setOnClickListener {
            Toast.makeText(this@BoardDetailActivity, "신고가 접수 되었습니다", Toast.LENGTH_SHORT).show()
        }
        val singo3 = view.findViewById<TextView>(R.id.singo_3)
        singo3.setOnClickListener {
            Toast.makeText(this@BoardDetailActivity, "신고가 접수 되었습니다", Toast.LENGTH_SHORT).show()
        }
        val singo4 = view.findViewById<TextView>(R.id.singo_4)
        singo4.setOnClickListener {
            Toast.makeText(this@BoardDetailActivity, "신고가 접수 되었습니다", Toast.LENGTH_SHORT).show()
        }
        val singo5 = view.findViewById<TextView>(R.id.singo_5)
        singo5.setOnClickListener {
            Toast.makeText(this@BoardDetailActivity, "신고가 접수 되었습니다", Toast.LENGTH_SHORT).show()
        }
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

    var sendupload : String? = null
    private fun clickSendUpload(){
        sendupload ?: return
    }
    private fun clickTitle() {
        val imageView = ImageView(this@BoardDetailActivity)
        imageView.setImageResource(R.drawable.baseline_image_post_sample)
        val builder = AlertDialog.Builder(this@BoardDetailActivity)
        builder.setView(imageView)
        val alertDialog = builder.create()
        alertDialog.show()
    }



}