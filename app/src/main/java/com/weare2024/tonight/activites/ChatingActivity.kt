package com.weare2024.tonight.activites

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.util.Base64Utils
import com.weare2024.tonight.G
import com.weare2024.tonight.adapter.ChatAdapter
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.data.ChatData2
import com.weare2024.tonight.data.ChatList
import com.weare2024.tonight.data.ChatList2
import com.weare2024.tonight.data.ChatRoomInfo
import com.weare2024.tonight.data.UserData
import com.weare2024.tonight.firebase.FBRef
import java.security.DigestException
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ChatingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatingBinding
//    lateinit var chatData: ChatList
    lateinit var chatData: ChatData2
    lateinit var roomInfo: ChatRoomInfo

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

        val combinedHash = getSign(sortedHash(ab,ac)).replace("/", "")

        if (intent != null && intent.hasExtra("chat_type")) {
            when(intent.getStringExtra("chat_type")) {
                "board" -> {
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
//                        chatData = Gson().fromJson(it, ChatList::class.java)
//                        AlertDialog.Builder(this).setMessage("$chatData").create().show()
                        if (G.nickname != yourNickname) {
                            binding.toolvar.title = yourNickname
                            binding.toolvar.subtitle = yourNickname
                        } else {
                            binding.toolvar.title = G.nickname
                            binding.toolvar.subtitle = G.nickname
                        }
                    }
                }
            }

        }
        binding.btnSend.setOnClickListener { btnSend() }
        binding.recyclerView.adapter = ChatAdapter(this, msgItem)

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

//        FBRef.testRef.document("chatRoom").collection(G.uid + youruid)
//            .addSnapshotListener { snap, e ->
//                if (e != null) {
//                    Toast.makeText(this@ChatingActivity, "${e.message}", Toast.LENGTH_SHORT).show()
//                }
//                if (snap != null) {
//                    val itemList: ArrayList<ChatData2> = ArrayList()
//                    for (snapshot in snap.documents) {
//                        val mssg = snapshot.toObject(ChatData2::class.java)
//                        if (mssg != null) {
//                            itemList.add(mssg)
//                        }
//
//                    }
//                }
//
//
//            }

//        AlertDialog.Builder(this).setMessage("$ab\n$ac\n ${ab == ac}").create().show()
//        AlertDialog.Builder(this).setMessage("$combinedHash1\n$combinedHash2").create().show()

    }

    private fun btnSend() {

        val yourUid = intent.getStringExtra("yourUid") ?: "에러!"
        val yourNickname = intent.getStringExtra("yourNickname") ?: "에러!"

        val msg = binding.et.text.toString()
        val time = SimpleDateFormat("hh:mm", Locale.KOREA).format(Date())
        var o = "오전"
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
            o = "오후"
        }
        val item = ChatData2(G.uid, G.nickname, msg, o + time)

        val t =
            "MSG_" + (SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date()))

        val s = G.uid + yourUid
        val s2 = yourUid + G.uid
        val ab = getSign(s)
        val ac = getSign(s2)

        val combinedHash = getSign(sortedHash(ab,ac)).replace("/", "")
//        val combinedHash2 = getSign(sortedHash(ac,ab))

//        AlertDialog.Builder(this).setMessage("$combinedHash1\n$combinedHash2").create().show()
//        val chatRoom = mutableMapOf<ChatRoomInfo, String>()
        
//        roomInfo = ChatRoomInfo(combinedHash, yourUid, G.uid, yourNickname, G.nickname)
//        val item2 = ChatList2(roomInfo, item)
        FBRef.testRef.document(combinedHash).set(item)
            .addOnSuccessListener {
                FBRef.testRef.document(combinedHash).get().addOnSuccessListener {
                    it.reference.collection(G.uid).document(t).set(item).addOnSuccessListener {  }
                    it.reference.collection(yourUid).document(t).set(item).addOnSuccessListener {  }
                }
                Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
//                FBRef.testRef.document(combinedHash).collection(G.uid).document(t).set(item)
//                    .addOnSuccessListener {
//                    }.addOnFailureListener {
//                        Toast.makeText(this@ChatingActivity, "${it.message}실패", Toast.LENGTH_SHORT).show()
//                    }
//                FBRef.testRef.document(combinedHash).collection(yourUid).document(t).set(item)
//                    .addOnSuccessListener {
//                    }.addOnFailureListener {
//                        Toast.makeText(this@ChatingActivity, "${it.message}실패", Toast.LENGTH_SHORT).show()
//                    }

            }.addOnFailureListener {
                Toast.makeText(this@ChatingActivity, "${it.message}실패", Toast.LENGTH_SHORT).show()
            }

//        FBRef.userRef.document(yourNickname).update("message", binding.et.text.toString() ?: "")

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



