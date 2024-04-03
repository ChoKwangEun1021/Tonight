package com.weare2024.tonight.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FBAuth {
    companion object {
        val auth = Firebase.auth

        fun getUid(): String {
            return auth.currentUser?.uid.toString()
        }
    }
}
