package com.weare2024.tonight.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weare2024.tonight.activites.ChatingActivity
import com.weare2024.tonight.data.SampleChatList

import com.weare2024.tonight.databinding.FragmentChatBinding
import com.weare2024.tonight.databinding.RecyclerViewChatListBinding


class ChatListAdapter(var context: Context,var chatlist:List<SampleChatList>) :Adapter<ChatListAdapter.VH>(){
    inner class VH(var binding: RecyclerViewChatListBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(context)
        var binding=RecyclerViewChatListBinding.inflate(layoutInflater,parent,false)
        return VH(binding)
    }

    override fun getItemCount(): Int {
        return chatlist.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val list = chatlist[position]
        holder.binding.tvLastText.text = list.aa
        holder.binding.tvNickName.text = list.ss

        holder.binding.root.setOnClickListener{
            val intent = Intent(context,ChatingActivity::class.java)


            context.startActivity(intent)
        }
    }

}