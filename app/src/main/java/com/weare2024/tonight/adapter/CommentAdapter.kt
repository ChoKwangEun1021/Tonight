package com.weare2024.tonight.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weare2024.tonight.activites.BoardDetailActivity
import com.weare2024.tonight.data.SampleComment
import com.weare2024.tonight.databinding.RecyclerCommentBinding

class CommentAdapter (var content : Context, var commentlist : List<SampleComment>): Adapter<CommentAdapter.VH>() {
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
        val list = commentlist[position]
        holder.binding.tvName.text = list.nickname
        holder.binding.tvComment.text = list.comment

        holder.binding.root.setOnClickListener {
            val intent = Intent(content,BoardDetailActivity::class.java)

            content.startActivity(intent)
        }
    }
}
