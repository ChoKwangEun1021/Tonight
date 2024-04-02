package com.weare2024.tonight.firebase

import android.annotation.SuppressLint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FBRef {
    @SuppressLint("StaticFieldLeak")
    companion object {
        val fs = Firebase.firestore

        val userRef = fs.collection("users")
        val boardRef = fs.collection("board")
        val chatRef = fs.collection("chat")
    }
}