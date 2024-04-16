package com.weare2024.tonight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.weare2024.tonight.databinding.PagerProfileBoardsBinding

class ProfilePagerAdapter(val context: Context, val items: List<String>) : Adapter<ProfilePagerAdapter.VH>() {
//    private lateinit var itemClickListner: OnItemClickListner
//    interface OnItemClickListner {
//        fun onItemClick(view: View?, position: Int)
//    }
//
//    fun setOnItemClickListner(onItemClickListner: OnItemClickListner) {
//        itemClickListner = onItemClickListner
//    }
    inner class VH(val binding: PagerProfileBoardsBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = PagerProfileBoardsBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        Glide.with(context).load(item).into(holder.binding.iv)

//        holder.binding.root.setOnClickListener {
//            itemClickListner.onItemClick(it, position)
//        }
    }
}