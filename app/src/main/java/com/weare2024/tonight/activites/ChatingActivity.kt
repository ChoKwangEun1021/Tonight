package com.weare2024.tonight.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.weare2024.tonight.G
import com.weare2024.tonight.adapter.ChatAdapter
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.data.ChatData
import com.weare2024.tonight.data.ChatData2
import com.weare2024.tonight.data.UserData
import com.weare2024.tonight.firebase.FBRef
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ChatingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val yourUid = intent.getStringExtra("yourUid") ?: "에러!"
        val yourNickname = intent.getStringExtra("yourNickname") ?: "에러!"
        val msgItem: MutableList<ChatData2> = mutableListOf()


        binding.toolvar.title = yourNickname
        binding.toolvar.subtitle = yourNickname
        binding.btnSend.setOnClickListener { btnSend() }
        binding.recyclerView.adapter = ChatAdapter(this, msgItem)

        FBRef.testRef.document("chatRoom").collection(G.uid + yourUid)
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

    }

    private fun btnSend() {

        var youruid = intent.getStringExtra("yourUid") ?: "에러!"
        var yourNickname = intent.getStringExtra("yourNickname") ?: "에러!"

        val nickname = G.nickname
        val image = G.uid
        val msg = binding.et.text.toString()
        val time = SimpleDateFormat("hh:mm", Locale.KOREA).format(Date())
        val uid = G.uid
        var o = "오전"
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
            o = "오후"
        }
        val item = ChatData2(uid, nickname, msg, o + time)

        val t =
            "MSG_" + (SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date()))
        // Firestore에 메시지 저장

        FBRef.testRef.document("chatRoom").collection(G.uid + youruid).document(t).set(item)
            .addOnSuccessListener {
                Toast.makeText(this@ChatingActivity, "성공", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@ChatingActivity, "${it.message}실패", Toast.LENGTH_SHORT).show()
            }


        FBRef.testRef.document("chatRoom").collection(youruid + G.uid).document(t).set(item)
            .addOnSuccessListener {
                Toast.makeText(this@ChatingActivity, "성공", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@ChatingActivity, "${it.message}실패", Toast.LENGTH_SHORT).show()
            }

        FBRef.userRef.document(yourNickname).update("message", binding.et.text.toString() ?: "")

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        binding.et.clearFocus()
        binding.et.text.clear()
        binding.recyclerView.adapter!!.notifyDataSetChanged()

    }
}



