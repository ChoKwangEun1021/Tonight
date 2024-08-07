package com.weare2024.tonight.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.weare2024.tonight.G
import com.weare2024.tonight.databinding.RecyclerViewMyChatBinding
import com.weare2024.tonight.databinding.RecyclerViewOtherChatBinding
import com.weare2024.tonight.data.ChatData2
import com.weare2024.tonight.firebase.FBRef

class ChatAdapter(var context: Context, var chatDataItem: List<ChatData2>) : Adapter<ViewHolder>() {

    inner class VH1(val binding: RecyclerViewMyChatBinding) : ViewHolder(binding.root)
    inner class VH2(val binding: RecyclerViewOtherChatBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        return if (viewType == 0) VH1(RecyclerViewMyChatBinding.inflate(layoutInflater,parent,false))
        else VH2(RecyclerViewOtherChatBinding.inflate(layoutInflater, parent, false))
    }
    override fun getItemCount(): Int = chatDataItem.size

    override fun getItemViewType(position: Int): Int {
        return if (G.uid == chatDataItem[position].yourUid) 0 else 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = chatDataItem[position]

        if (item.yourUid == G.uid) {
            val vh = holder as VH1
            vh.binding.tvName.text = item.yourNickname
            vh.binding.tvMsg.text = item.message
            vh.binding.tvTime.text = item.time
            val imgRef = Firebase.storage.getReference("usersImg/${item.yourUid}")
            imgRef.downloadUrl.addOnSuccessListener {
                Glide.with(context).load(it).into(vh.binding.ciriv)
            }

        } else {
            val vh = holder as VH2
//            FBRef.userRef.whereEqualTo("uid", item.yourUid).get().addOnSuccessListener {
//                for (data in it) {
//                    vh.binding.tvName.text = data["nickname"].toString()
//                }
//            }
            vh.binding.tvName.text = item.yourNickname
            vh.binding.tvMsg.text = item.message
            vh.binding.tvTime.text = item.time
            val imgRef = Firebase.storage.getReference("usersImg/${item.yourUid}")
            imgRef.downloadUrl.addOnSuccessListener {
                Glide.with(context).load(it).into(vh.binding.ciriv)
            }

        }

    }
}