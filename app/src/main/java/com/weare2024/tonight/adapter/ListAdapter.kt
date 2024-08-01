package com.weare2024.tonight.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.weare2024.tonight.activites.BoardDetailActivity
import com.weare2024.tonight.data.Images
import com.weare2024.tonight.databinding.ListItemBinding

class ListAdapter(val context: Context, val imageList: List<Images>) : Adapter<ListAdapter.VH>() {

    inner class VH(val binding: ListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater= LayoutInflater.from(context)
        val binding= ListItemBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }
    override fun getItemCount(): Int {
        return imageList.size
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = imageList[position]

        val imageUri = "http://weare2024.dothome.co.kr/Tonight/board/${item.imgsUri}"
        Glide.with(context).load(imageUri).into(holder.binding.iv)

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, BoardDetailActivity::class.java)
            intent.putExtra("boardNo", item.boardNo)
            context.startActivity(intent)
        }

    }

}