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
import com.weare2024.tonight.data.ProfileImages
import com.weare2024.tonight.databinding.PagerProfileBoardsBinding

class ProfilePagerAdapter(val context: Context, val items: List<ProfileImages>) : Adapter<ProfilePagerAdapter.VH>() {
    inner class VH(val binding: PagerProfileBoardsBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = PagerProfileBoardsBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        val imgsUri = "http://weare2024.dothome.co.kr/Tonight/board/${item.imgsUri}"
        Glide.with(context).load(imgsUri).into(holder.binding.iv)

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, BoardDetailActivity::class.java)
            intent.putExtra("boardNo", item.boardNo)
            context.startActivity(intent)
        }
//        Log.i("TAG", "$item")

    }
}