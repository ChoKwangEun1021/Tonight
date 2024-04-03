package com.weare2024.tonight.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
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
        val images= imageList[position]

        holder.binding.iv.setImageResource(images.imgId)

        holder.binding.root.setOnClickListener {
//            Toast.makeText(context, "${position}번 BoardDetail Activity로 연결", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context,BoardDetailActivity::class.java)
//            context.startActivity(intent)
            val intent = Intent(context,BoardDetailActivity::class.java)
            context.startActivity(intent)
        }
    }
}