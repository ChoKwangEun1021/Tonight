package com.weare2024.tonight.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.weare2024.tonight.activites.BoardDetailActivity
import com.weare2024.tonight.data.SampleComment
import com.weare2024.tonight.databinding.RecyclerCommentBinding

class CommentAdapter(var content: Context, var commentlist: MutableMap<String, String>): Adapter<CommentAdapter.VH>() {
    inner class VH(var binding: RecyclerCommentBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(content)
        var binding = RecyclerCommentBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return commentlist.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
//        val list = commentlist[position]
//        holder.binding.tvName.text = list.nickname
//        holder.binding.tvComment.text = list.content
//
//        // 이미지를 보여주기 [ DB에는 이미지경로가 "./upload/IMG_xxxxx.jpg" 임 ]
//        // 안드로이드 에서는 서버의 전체 주소가 필요함
//        val imgUrl = "http://weare2024.dothome.co.kr${item.file}"
//        //주소가 올바른지 확인
//        Log.d("imgUrl", imgUrl)
//
//        Glide.with(context).load(imgUrl).into(holder.binding.iv)

        // 이미지 보여주기 [ DB에는 이미지경로가 "./boardImgs/IMG_xxxx.jpg" 이기때문에 ]
        // 안드로이드 에서는 서버의 전체 주소가 필요함
//        val imgUrl = "http://nameskdlxm.dothome.co.kr/05Retrofit/${item.file}"
//        //주소가 올바른지 확인하기
//        Log.d("imgUrl",imgUrl)
//
//        Glide.with(context).load(imgUrl).into(holder.binding.iv)

//        holder.binding.root.setOnClickListener {
//            val intent = Intent(content,BoardDetailActivity::class.java)
//
//            content.startActivity(intent)
//        }
    }
}
