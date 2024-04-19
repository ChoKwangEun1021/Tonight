package com.weare2024.tonight.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.google.gson.Gson
import com.weare2024.tonight.G
import com.weare2024.tonight.activites.ChatingActivity
import com.weare2024.tonight.data.ChatList
import com.weare2024.tonight.data.ChatList2
import com.weare2024.tonight.data.HomeData
import com.weare2024.tonight.data.LastChatData
import com.weare2024.tonight.data.UserData


import com.weare2024.tonight.databinding.RecyclerViewChatListBinding
import com.weare2024.tonight.firebase.FBRef


class ChatListAdapter(var context: Context, var items: MutableList<ChatList2>) :Adapter<ChatListAdapter.VH>() {
    inner class VH(var binding: RecyclerViewChatListBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerViewChatListBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val roomInfo = items[position].chatRoom
        val chatData = items[position].chat

//        if (roomInfo.myUid == G.uid) {
//            holder.binding.tvNickName.text = roomInfo.yourNickname
//            holder.binding.tvLastText.text = chatData.message
//            val imgRef: StorageReference = Firebase.storage.getReference("usersImg/${roomInfo.yourUid}")
//            imgRef.downloadUrl.addOnSuccessListener {
//                Glide.with(context).load(it).into(holder.binding.civ)
//            }
//        } else {
//            holder.binding.tvNickName.text = roomInfo.myNickname
//            holder.binding.tvLastText.text = chatData.message
//            val imgRef: StorageReference = Firebase.storage.getReference("usersImg/${roomInfo.myUid}")
//            imgRef.downloadUrl.addOnSuccessListener {
//                Glide.with(context).load(it).into(holder.binding.civ)
//            }
//        }

        holder.binding.root.setOnClickListener {

            val intent = Intent(context, ChatingActivity::class.java)
            val gson = Gson()
            val data = gson.toJson(items)
            intent.putExtra("data", data)
            intent.putExtra("chat_type", "chatList")
            context.startActivity(intent)
        }

    }

}