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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.adapter.CommentAdapter
import com.weare2024.tonight.data.CommentData
import com.weare2024.tonight.databinding.ActivityBoardDetailBinding
import com.weare2024.tonight.network.RetrofitHelper
import com.weare2024.tonight.network.RetrofitService

class BoardDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBoardDetailBinding.inflate(layoutInflater) }
    private val bs: View by lazy { binding.bs } //댓글 바텀시트
    private val bsb: BottomSheetBehavior<View> by lazy { BottomSheetBehavior.from(bs) }
    private val rl_title: View by lazy { binding.rlTitle }
    private val commentList: MutableMap<String, String> = mutableMapOf()
    private var imgPath: String? = null
    val vp: ViewPager2 by lazy { binding.viewPager }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.request.setOnClickListener { clickComment() }
        binding.chat.setOnClickListener { clickChat() }
//        binding.sendupload.setOnClickListener { clickSendUpload() }
        binding.rlTitle.setOnClickListener { clickTitle() }
        binding.recyclerComment.adapter = CommentAdapter(this, commentList)
        binding.rl.background = null
        binding.toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item!!.itemId == R.id.more112) {
                    showBottomSheet()
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
//        loadDB()
//    }
////    private fun loadDB(){    // 요거는 이제 게시 상태페이지 댓글리스트 불러 오기 위한 준비물
////        val retrofit =RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
////        val retrofitService = retrofit.create(RetrofitService::class.java)
////        retrofitService.insertBoard(itemList,)

        //    private fun insertData() {
//        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
//        val retrofitService = retrofit.create(RetrofitService::class.java)
//
//    }
////
////
    }
    private fun showBottomSheet() {
        val dailog = BottomSheetDialog(this@BoardDetailActivity)
        val view = layoutInflater.inflate(R.layout.more112, null)
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
    private fun clickComment() {
        if (bsb.state == BottomSheetBehavior.STATE_COLLAPSED)  // 상태 확인
            bsb.state = BottomSheetBehavior.STATE_EXPANDED // 시트 열기
        else {
            bsb.state = BottomSheetBehavior.STATE_COLLAPSED
        }  // 시트 닫기
    }
    private fun clickChat() {
        Toast.makeText(this, "채팅 채널로 연결 됩니다.", Toast.LENGTH_SHORT).show()
    }
    var sendupload: String? = null
    private fun clickSendUpload() { // 댓글 달면 DB에 저장시키고  리사이클러뷰에 띄우는 과정
        sendupload ?: return
        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
        val retrofitService = retrofit.create(RetrofitService::class.java)
        commentList["uid"] = "uid"
        commentList["nickname"] = "nickname"
        commentList["comment"] = binding.et.text.toString()
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