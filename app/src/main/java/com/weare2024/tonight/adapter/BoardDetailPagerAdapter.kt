package com.weare2024.tonight.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.weare2024.tonight.databinding.PagerImgUploadBinding

class BoardDetailPagerAdapter (val context: Context, val imgs: MutableList<Uri?>) : Adapter<BoardDetailPagerAdapter.VH>() {

    inner class VH(val binding: PagerImgUploadBinding) : ViewHolder(binding.root){
        val iv= binding.iv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater= LayoutInflater.from(context)
        val binding= PagerImgUploadBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return imgs.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(imgs[position]).into(holder.iv)
    }
}