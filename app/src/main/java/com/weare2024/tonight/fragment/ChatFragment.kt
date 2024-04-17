package com.weare2024.tonight.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weare2024.tonight.G
import com.weare2024.tonight.adapter.ChatListAdapter
import com.weare2024.tonight.data.ChatList
import com.weare2024.tonight.data.LastChatData
import com.weare2024.tonight.data.UserData

import com.weare2024.tonight.databinding.FragmentChatBinding
import com.weare2024.tonight.firebase.FBRef

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    var d = 0
    val user: MutableList<UserData> = mutableListOf()
    val listItem = mutableListOf<ChatList>()
    val lastItem = mutableListOf<LastChatData>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        FBRef.userRef.get().addOnSuccessListener { userSnapshot ->
            val users = mutableListOf<UserData>()
            val userws = mutableListOf<LastChatData>()

            userSnapshot.documents.forEach { document ->
                val user = document.toObject(UserData::class.java)
                user?.let { users.add(it) }
                users.forEach { user ->
                    val userUid = user.uid
                    FBRef.chatRef.document("sas").collection(G.uid + userUid)
                        .get()
                        .addOnSuccessListener { chatSnapshot ->
                            chatSnapshot.documents.forEach { chatDocument ->
                                val chatData = chatDocument.toObject(ChatList::class.java)
                                chatData?.let {
                                    users.forEach {
                                        if (it.message != "") {
                                            val sw = LastChatData(it.message, it.nickname, it.uid)
                                            lastItem.add(sw)
                                        }
                                    }
                                }
                                binding.recyclerView.adapter =ChatListAdapter(requireContext(), userws)
                            }

                        }

                }

            }


        }

    }

}



