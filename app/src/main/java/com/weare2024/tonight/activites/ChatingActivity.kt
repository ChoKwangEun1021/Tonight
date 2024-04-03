package com.weare2024.tonight.activites

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.adapter.ChatAdapter
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.firebase.ChatData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatingActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatingBinding
    var room: String = " Room #1 "
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityChatingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val msgItem: MutableList<ChatData> = mutableListOf()

        binding.toolvar.title = room
        binding.toolvar.subtitle = G.name
        binding.btnSend.setOnClickListener { btnSend() }
        binding.recyclerView.adapter = ChatAdapter(this,msgItem)

        val chatRef: CollectionReference = Firebase.firestore.collection(room)
        chatRef.addSnapshotListener { value, error ->
            value?.documentChanges?.forEach {
                val snapshot = it.document
                val item = snapshot.toObject(ChatData::class.java)
                item?.apply {
                    msgItem.add(this)
                    binding.recyclerView.adapter!!.notifyItemInserted(msgItem.size-1)
                    binding.recyclerView.scrollToPosition(msgItem.size - 1)
                }
            }
        }

    }

    private fun btnSend() {
        val nickname = G.name
        val image = G.image
        val msg = binding.et.text.toString()
        val time = SimpleDateFormat("hh:mm", Locale.KOREA).format(Date())
        val uid =G.uid

        val item = ChatData(uid,nickname, msg, time, image)
        val chatRef: CollectionReference = Firebase.firestore.collection(room)
        val t = "MSG_" + (SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(Date()))
        chatRef.document("s").set(item)
        binding.et.setText(t)
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE)  as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


    }
}