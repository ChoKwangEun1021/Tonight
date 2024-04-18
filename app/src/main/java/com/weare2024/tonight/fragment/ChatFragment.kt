package com.weare2024.tonight.fragment

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
import com.weare2024.tonight.data.ChatList
import com.weare2024.tonight.data.LastChatData
import com.weare2024.tonight.data.UserData

import com.weare2024.tonight.databinding.FragmentChatBinding
import com.weare2024.tonight.firebase.FBRef
import java.security.DigestException
import java.security.MessageDigest

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    var d = 0
    val user: MutableList<UserData> = mutableListOf()
    val listItem = mutableListOf<ChatList>()

    //    val lastItem = mutableListOf<LastChatData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
//
//        }

    }

    private fun getChatList() {
        val ma = activity as MainActivity
        val uids = ma.uids

        for (i in 0 until uids.size) {
            val s = G.uid + uids[i]
            val s2 = uids[i] + G.uid
            val ab = getSign(s)
            val ac = getSign(s2)

            val combinedHash1 = getSign(sortedHash(ab,ac))

            if (FBRef.testRef.document("chatRoom").collection(combinedHash1).get().isSuccessful) {
                FBRef.testRef.document("chatRoom").collection(combinedHash1).get().addOnSuccessListener {
                    val data = it.documents
                    data.forEach { snap ->
                        listItem.add(ChatList(snap["myUid"].toString(), snap["yourUid"].toString(), snap["yourNickname"].toString(), snap["message"].toString(), snap["time"].toString()))
                    }
                }
            }
        }
//
//        AlertDialog.Builder(requireContext()).setMessage("${uids}").create().show()

//        for (i in 0 until uids.size) {
//            FBRef.testRef.document(G.uid).collection(uids[i]).get().addOnSuccessListener {
//                val a = it.toObjects(ChatList::class.java)
//                it.documents.forEach {
//                    listItem.add(ChatList(it["yourUid"].toString(), it["time"].toString(), it["message"].toString()))
//                }
//                AlertDialog.Builder(requireContext()).setMessage("${listItem.size}").create().show()
//                val data = it.data
//                it.reference.collection("wwDw1wOZ7mYfRiTfYj1hX0BTfC23").get().addOnSuccessListener {
//                    for (data in it) {
//                        AlertDialog.Builder(requireContext()).setMessage("${data.id}\n${data.data}").create().show()
//                    }
//                }
//                Toast.makeText(requireContext(), "실행 테스트", Toast.LENGTH_SHORT).show()
//                AlertDialog.Builder(requireContext()).setMessage("${data.id}\n${data.data}").create().show()
//                Log.d("qwer", "${data.id}\n${data.data}")
//            }
//        }

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



