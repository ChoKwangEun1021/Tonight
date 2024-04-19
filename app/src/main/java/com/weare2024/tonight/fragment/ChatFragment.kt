package com.weare2024.tonight.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.gms.common.util.Base64Utils
import com.weare2024.tonight.G
import com.weare2024.tonight.activites.MainActivity
import com.weare2024.tonight.adapter.ChatListAdapter
import com.weare2024.tonight.data.ChatData2
import com.weare2024.tonight.data.ChatList
import com.weare2024.tonight.data.ChatList2
import com.weare2024.tonight.data.ChatRoomInfo
import com.weare2024.tonight.data.LastChatData
import com.weare2024.tonight.data.UserData

import com.weare2024.tonight.databinding.FragmentChatBinding
import com.weare2024.tonight.firebase.FBRef
import java.security.DigestException
import java.security.MessageDigest

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var roomInfo: ChatRoomInfo
    private val listItem = mutableListOf<ChatList2>()
    private val combinedHash = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        binding.recyclerView.adapter = ChatListAdapter(requireContext(), listItem)
        getChatList()
//        FBRef.userRef.get().addOnSuccessListener { userSnapshot ->
//            val users = mutableListOf<UserData>()
//            val userws = mutableListOf<LastChatData>()
//
//            userSnapshot.documents.forEach { document ->
//                val user = document.toObject(UserData::class.java)
//                user?.let { users.add(it) }
//                users.forEach { user ->
//                    val userUid = user.uid
//                    FBRef.testRef.document("chatRoom").collection(G.uid + userUid)
//                        .get()
//                        .addOnSuccessListener { chatSnapshot ->
//                            chatSnapshot.documents.forEach { chatDocument ->
//                                val chatData = chatDocument.toObject(ChatList::class.java)
//                                chatData?.let {
//                                        listItem.add(it)
//                                }
//                                binding.recyclerView.adapter =ChatListAdapter(requireContext(), users)
//                            }
//
//                        }
//
//                }
//
//            }
//
//        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getChatList() {
        val ma = activity as MainActivity
        val uids = ma.uids

        for (i in 0 until uids.size) {
            val s = G.uid + uids[i]
            val s2 = uids[i] + G.uid
            val ab = getSign(s)
            val ac = getSign(s2)
            val chatKey = getSign(sortedHash(ab,ac)).replace("/", "")
            combinedHash.add(chatKey)

            FBRef.testRef.document(combinedHash[i]).get().addOnSuccessListener {
//                roomInfo = ChatRoomInfo(it.data?.get("roomName").toString(), it.data?.get("yourUid").toString(), it.data?.get("myUid").toString(), it.data?.get("yourNickname").toString(), it.data?.get("myNickname").toString())
//                roomInfo = ChatRoomInfo(it.data)
                AlertDialog.Builder(requireContext()).setMessage("${it.data}").create().show()
                it.reference.collection(G.uid).get().addOnSuccessListener {it2 ->
                    val data = it2.documents

                    data.forEach { snap ->
                        listItem.add(ChatList2(roomInfo, ChatData2(snap["yourUid"].toString(), snap["yourNickname"].toString(), snap["message"].toString(), snap["time"].toString())))

//                        binding.recyclerView.adapter!!.notifyItemInserted(0)
//                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }

//                AlertDialog.Builder(requireContext()).setMessage("${it?.data?.get("roomName")}").create().show()
//                val data = it.documents
//                data.forEach { snap ->
//                    listItem.add(ChatList2(snap["yourUid"].toString(), snap["yourNickname"].toString(), snap["message"].toString(), snap["time"].toString()))
//                    AlertDialog.Builder(requireContext()).setMessage("$listItem").create().show()
//                    binding.recyclerView.adapter!!.notifyItemInserted(0)
//                    binding.recyclerView.adapter!!.notifyDataSetChanged()
//                }
            }
        }

    }

    private fun sortedHash(hash1: String, hash2: String): String {
        return if (hash1 < hash2) {
            hash1 + hash2
        } else {
            hash2 + hash1
        }
    }

    private fun getSign(input: String): String{
        val hash: ByteArray
        try{
            val md = MessageDigest.getInstance("SHA-256")
            md.update(input.toByteArray())
            hash = md.digest()
        }catch (e: CloneNotSupportedException){
            throw DigestException("couldn't make digest of patial content")
        }
        return Base64Utils.encode(hash)
    }

}



