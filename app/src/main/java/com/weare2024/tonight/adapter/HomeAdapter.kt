package com.weare2024.tonight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.weare2024.tonight.data.HomeData
import com.weare2024.tonight.databinding.RecyclerViewHomeItemBinding

class HomeAdapter(val context: Context, val itemList: List<HomeData>) : Adapter<HomeAdapter.VH>() {
    inner class VH(val binding: RecyclerViewHomeItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerViewHomeItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = itemList[position]

        Glide.with(context).load(item.uid).into(holder.binding.ivProfile)
        holder.binding.tvNickName.text = item.nickname
        holder.binding.tvAge.text = item.age
        holder.binding.tvArea.text = item.area
    }
}