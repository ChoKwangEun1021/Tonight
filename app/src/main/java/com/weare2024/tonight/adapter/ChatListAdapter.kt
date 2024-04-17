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
import com.weare2024.tonight.activites.ChatingActivity
import com.weare2024.tonight.data.LastChatData


import com.weare2024.tonight.databinding.RecyclerViewChatListBinding


class ChatListAdapter(var context: Context, var lastChatData: MutableList<LastChatData>) :Adapter<ChatListAdapter.VH>() {
    inner class VH(var binding: RecyclerViewChatListBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(context)
        var binding = RecyclerViewChatListBinding.inflate(layoutInflater, parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int {

        return lastChatData.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        val last = lastChatData[position]

        holder.binding.tvNickName.text = last.nickname
        holder.binding.tvLastText.text = last.message
        val imgRef: StorageReference = Firebase.storage.getReference("usersImg/" + last.uid)
        imgRef.downloadUrl.addOnSuccessListener {
            Glide.with(context).load(it).into(holder.binding.civ)
        }

        holder.binding.root.setOnClickListener {

            val intent = Intent(context, ChatingActivity::class.java)
            intent.putExtra("yourUid", last.uid)

            context.startActivity(intent)
        }

    }

}