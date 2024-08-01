package com.weare2024.tonight.activites

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.adapter.CommentAdapter
import com.weare2024.tonight.data.BoardDetailData
import com.weare2024.tonight.data.CommentData
import com.weare2024.tonight.databinding.ActivityCommentBinding
import com.weare2024.tonight.network.RetrofitHelper
import com.weare2024.tonight.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCommentBinding.inflate(layoutInflater) }
    private val items = mutableListOf<CommentData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerComment.adapter = CommentAdapter(this, items)
        binding.btnComment.setOnClickListener { insertComment() }
        binding.toolbar.setNavigationOnClickListener { finish() }
        selectComment()
    }

    override fun onResume() {
        super.onResume()
        selectComment()
    }

    private fun insertComment() {
        val boardNo = intent.getIntExtra("boardNo", 0)
        val cmtUid = G.uid
        val cmtNickname = G.nickname
        val cmtContent = binding.etContent.text.toString()

        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
        val retrofitService = retrofit.create(RetrofitService::class.java)
        retrofitService.commentInsert(boardNo, cmtUid, cmtNickname, cmtContent).enqueue(object : Callback<String> {
            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                val data = p1.body()
                Log.d("success", "$data")
            }

            override fun onFailure(p0: Call<String>, p1: Throwable) {
                Log.d("insertErr", "${p1.message}")
            }

        })

        binding.etContent.text.clear()
        binding.etContent.clearFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.btnComment.windowToken, 0)
        Toast.makeText(this, "댓글이 등록 되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun selectComment() {
        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val boardNo = intent.getIntExtra("boardNo", 0)

        retrofitService.commentNoSend(boardNo).enqueue(object : Callback<List<CommentData>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(p0: Call<List<CommentData>>, p1: Response<List<CommentData>>) {

                items.clear()

                val items2: List<CommentData>? = p1.body()
                items2?.forEach {
                    items.add(0, it)
                    binding.recyclerComment.adapter!!.notifyItemInserted(items.size - 1)
                }

                binding.recyclerComment.adapter!!.notifyDataSetChanged()
                //AlertDialog.Builder(this@CommentActivity).setMessage("${p1.body()} $boardNo").create().show()
            }

            override fun onFailure(p0: Call<List<CommentData>>, p1: Throwable) {
                Log.d("qwer1", "${p1.message}")
            }

        })
    }
}