package com.weare2024.tonight.activites

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

        selectComment()
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
                    binding.recyclerComment.adapter!!.notifyItemInserted(0)
                }

                binding.recyclerComment.adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(p0: Call<List<CommentData>>, p1: Throwable) {
                Log.d("qwer1", "${p1.message}")
            }

        })
    }
}