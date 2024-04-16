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
import com.weare2024.tonight.G
import com.weare2024.tonight.adapter.ChatAdapter
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.data.ChatData
import com.weare2024.tonight.firebase.FBRef
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ChatingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatingBinding
    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val msgItem: MutableList<ChatData> = mutableListOf()


        binding.toolvar.title = G.nickname
        binding.toolvar.subtitle = G.nickname
        binding.btnSend.setOnClickListener { btnSend() }
        binding.recyclerView.adapter = ChatAdapter(this, msgItem)


        FBRef.chatRef.document("sas").collection(G.nickname).addSnapshotListener { value, error ->
            value?.documentChanges?.forEach {
                val snapshot = it.document
                val item = snapshot.toObject(ChatData::class.java)
                item?.apply {
                    msgItem.add(this)
                    binding.recyclerView.adapter!!.notifyItemInserted(msgItem.size - 1)
                    binding.recyclerView.scrollToPosition(msgItem.size - 1)
                }
            }
        }

    }

    private fun btnSend() {
        val messageText = binding.et.text.toString()
        val message = hashMapOf(
            "senderId" to G.nickname,
            "message" to messageText,
            "timestamp" to FieldValue.serverTimestamp()
        )
        val nickname = G.nickname
        val image = G.uid
        val msg = binding.et.text.toString()
        val time = SimpleDateFormat("hh:mm", Locale.KOREA).format(Date())
        val uid = G.uid
        var o = "오전"
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
            o = "오후"
        }
        val item = ChatData(uid, nickname, msg, o + time, image)

        val t =
            "MSG_" + (SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date()))
        // Firestore에 메시지 저장

        FBRef.chatRef.document("sas").collection(G.nickname).document().set(item).addOnSuccessListener {
            Toast.makeText(this@ChatingActivity, "성공", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this@ChatingActivity, "${it.message}실패", Toast.LENGTH_SHORT).show()
        }
        FBRef.chatRef.document("sas").collection(G.nickname).document()
            .addSnapshotListener { snap, e ->
//                if (e != null) {
//                    Toast.makeText(this@ChatingActivity, "${e.message}", Toast.LENGTH_SHORT).show()
//                }
//                if (snap != null) {
//                    val itemList: ArrayList<ChatData> = ArrayList()
//                    for (snapshot in snap.)
//                }


            }

//        db.collection("chat").document("빵빵아").collection("옥지얌")
//            .orderBy("timestamp", Query.Direction.DESCENDING)
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    return@addSnapshotListener
//                }
//                if (snapshot != null) {
//                    val messagesList = ArrayList<ChatData>()
//                    for (doc in snapshot.documents) {
//                        val message = doc.toObject(ChatData::class.java)
//                        if (message != null) {
//                            messagesList.add(message)
//                        }
//                    }


        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        binding.et.clearFocus()
        binding.et.text.clear()


    }
}