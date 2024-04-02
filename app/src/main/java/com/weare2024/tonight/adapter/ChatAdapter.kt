package com.weare2024.tonight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.weare2024.tonight.databinding.RecyclerViewMyChatBinding
import com.weare2024.tonight.databinding.RecyclerViewOtherChatBinding
import com.weare2024.tonight.firebase.ChatData

class ChatAdapter(var context: Context,var chatDataItem:List<ChatData> ): Adapter<ViewHolder>() {

    inner class VH1(val binding:RecyclerViewMyChatBinding) :ViewHolder(binding.root)
    inner class VH2(val binding:RecyclerViewOtherChatBinding) :ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val laoutInflater =LayoutInflater.from(context)

        return if (viewType == 0) VH1(RecyclerViewMyChatBinding.inflate(laoutInflater,parent,false))
        else VH2(RecyclerViewOtherChatBinding.inflate(laoutInflater,parent,false))
    }

    override fun getItemCount(): Int {
        return chatDataItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}