package com.weare2024.tonight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weare2024.tonight.data.CommentData
import com.weare2024.tonight.databinding.RecyclerCommentBinding

class CommentAdapter (var content : Context, var items : List<CommentData>): Adapter<CommentAdapter.VH>() {
    inner class VH(var binding: RecyclerCommentBinding): ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerCommentBinding.inflate(LayoutInflater.from(content), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val list = items[position]
        holder.binding.tvName.text = list.cmtNickname
        holder.binding.tvComment.text = list.cmtContent
    }
}
