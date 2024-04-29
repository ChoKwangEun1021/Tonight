package com.weare2024.tonight.activites

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.util.Base64Utils
import com.google.gson.Gson
import com.weare2024.tonight.G
import com.weare2024.tonight.adapter.ChatAdapter
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.data.ChatData2
import com.weare2024.tonight.data.ChatList
import com.weare2024.tonight.data.ChatList2
import com.weare2024.tonight.data.ChatRoom
import com.weare2024.tonight.data.ChatRoomInfo
import com.weare2024.tonight.firebase.FBRef
import java.security.DigestException
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ChatingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatingBinding
    lateinit var chatRoom: ChatRoom
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val yourUid = intent.getStringExtra("yourUid") ?: "에러!"
        val yourNickname = intent.getStringExtra("yourNickname") ?: "에러!"
        val msgItem: MutableList<ChatData2> = mutableListOf()

        val s = G.uid + yourUid
        val s2 = yourUid + G.uid
        val ab = getSign(s)
        val ac = getSign(s2)

        val combinedHash = getSign(sortedHash(ab, ac)).replace("/", "")

        if (intent != null && intent.hasExtra("chat_type")) {
            when (intent.getStringExtra("chat_type")) {
                "board" -> {
                    FBRef.testRef.document(combinedHash).collection(G.uid)
                        .addSnapshotListener { value, error ->
                            value?.documentChanges?.forEach {
                                val snapshot = it.document
                                val item = snapshot.toObject(ChatData2::class.java)
                                msgItem.add(item)
                                binding.recyclerView.adapter!!.notifyItemInserted(msgItem.size - 1)
                                binding.recyclerView.scrollToPosition(msgItem.size - 1)
                            }
                        }

                    if (G.nickname != yourNickname) {
                        binding.toolvar.title = yourNickname
                        binding.toolvar.subtitle = yourNickname
                    } else {
                        binding.toolvar.title = G.nickname
                        binding.toolvar.subtitle = G.nickname
                    }
                }

                "chatList" -> {
                    val data = intent.getStringExtra("data")
                    data.also {
                        chatRoom = Gson().fromJson(it, ChatRoom::class.java)
                        AlertDialog.Builder(this).setMessage("$chatRoom").create().show()
                        FBRef.testRef.document(chatRoom.roomName).collection(G.uid)
                            .addSnapshotListener { value, error ->
                                value?.documentChanges?.forEach {
                                    val snapshot = it.document
                                    val item = snapshot.toObject(ChatData2::class.java)
                                    msgItem.add(item)
                                    binding.recyclerView.adapter!!.notifyItemInserted(msgItem.size - 1)
                                    binding.recyclerView.scrollToPosition(msgItem.size - 1)
                                }
                            }

                        if (G.nickname != chatRoom.yourNickname) {
                            binding.toolvar.title = chatRoom.yourNickname
                            binding.toolvar.subtitle = chatRoom.yourNickname
                        } else {
                            binding.toolvar.title = chatRoom.myNickname
                            binding.toolvar.subtitle = chatRoom.myNickname
                        }
                    }
                }
            }

        }
        binding.btnSend.setOnClickListener { btnSend() }
        binding.recyclerView.adapter = ChatAdapter(this, msgItem)

    }

    private fun btnSend() {
        val msg = binding.et.text.toString()
        val t = "MSG_" + (SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date()))
        val time = SimpleDateFormat("hh:mm", Locale.KOREA).format(Date())
        var o = "오전"
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
            o = "오후"
        }
        val item = ChatData2(G.uid, G.nickname, msg, o + time)

        if (intent != null && intent.hasExtra("chat_type")) {
            when (intent.getStringExtra("chat_type")) {
                "board" -> {
                    val yourUid = intent.getStringExtra("yourUid") ?: "에러!"
                    val yourNickname = intent.getStringExtra("yourNickname") ?: "에러!"

                    val s = G.uid + yourUid
                    val s2 = yourUid + G.uid
                    val ab = getSign(s)
                    val ac = getSign(s2)

                    val combinedHash = getSign(sortedHash(ab, ac)).replace("/", "")

                    val item2 =
                        ChatRoom(combinedHash, yourUid, G.uid, yourNickname, G.nickname, msg)

                    if (FBRef.testRef.document(combinedHash).get().isSuccessful) {
                        FBRef.testRef.document(combinedHash).get().addOnSuccessListener {
                            it.reference.collection(G.uid).document(t).set(item)
                                .addOnSuccessListener { }
                            it.reference.collection(yourUid).document(t).set(item)
                                .addOnSuccessListener { }
                            it.reference.update("lastMessage", msg).addOnSuccessListener { }
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {
                            Toast.makeText(
                                this@ChatingActivity,
                                "${it.message}실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        FBRef.testRef.document(combinedHash).set(item2)
                            .addOnSuccessListener {
                                FBRef.testRef.document(combinedHash).get().addOnSuccessListener {
                                    it.reference.collection(G.uid).document(t).set(item)
                                        .addOnSuccessListener { }
                                    it.reference.collection(yourUid).document(t).set(item)
                                        .addOnSuccessListener { }
                                    it.reference.update("lastMessage", msg).addOnSuccessListener { }
                                }
                                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()

                            }.addOnFailureListener {
                                Toast.makeText(
                                    this@ChatingActivity,
                                    "${it.message}실패",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                    }
                }

                "chatList" -> {
                    val data = intent.getStringExtra("data")

                    data.also {
                        chatRoom = Gson().fromJson(it, ChatRoom::class.java)
                        val item2 = ChatRoom(
                            chatRoom.roomName,
                            chatRoom.yourUid,
                            chatRoom.myUid,
                            chatRoom.yourNickname,
                            chatRoom.myNickname,
                            msg
                        )

                        FBRef.testRef.document(chatRoom.roomName).get().addOnSuccessListener {
                            it.reference.collection(chatRoom.myUid).document(t).set(item)
                                .addOnSuccessListener { }
                            it.reference.collection(chatRoom.yourUid).document(t).set(item)
                                .addOnSuccessListener { }
                            it.reference.update("lastMessage", msg).addOnSuccessListener { }
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()

                        }.addOnFailureListener {
                            Toast.makeText(
                                this@ChatingActivity,
                                "${it.message}실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        
//                        else {
//                            FBRef.testRef.document(chatRoom.roomName).set(item2)
//                                .addOnSuccessListener {
//                                    FBRef.testRef.document(chatRoom.roomName).get().addOnSuccessListener {
//                                        it.reference.collection(chatRoom.myUid).document(t).set(item)
//                                            .addOnSuccessListener { }
//                                        it.reference.collection(chatRoom.yourUid).document(t).set(item)
//                                            .addOnSuccessListener { }
//                                        it.reference.update("lastMessage", msg).addOnSuccessListener { }
//                                    }
//                                    Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
//
//                                }.addOnFailureListener {
//                                    Toast.makeText(this@ChatingActivity, "${it.message}실패", Toast.LENGTH_SHORT)
//                                        .show()
//                                }
//                        }
                    }

                }
            }


        }

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        binding.et.clearFocus()
        binding.et.text.clear()
//        binding.recyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun sortedHash(hash1: String, hash2: String): String {
        return if (hash1 < hash2) {
            hash1 + hash2
        } else {
            hash2 + hash1
        }
    }

    private fun getSign(input: String): String {
        val hash: ByteArray
        try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(input.toByteArray())
            hash = md.digest()
        } catch (e: CloneNotSupportedException) {
            throw DigestException("couldn't make digest of patial content")
        }
        return Base64Utils.encode(hash)
    }

}



