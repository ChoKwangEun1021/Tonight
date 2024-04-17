package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.G
import com.weare2024.tonight.databinding.ActivityEmailLoginBinding
import com.weare2024.tonight.firebase.FBAuth
import com.weare2024.tonight.firebase.FBRef

class EmailLoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }
    private val spf by lazy { getSharedPreferences("loginSave", MODE_PRIVATE) }
    private val spf2 by lazy { getSharedPreferences("userInfo", MODE_PRIVATE) }
    private val spfEdt by lazy { spf.edit() }
    private val spf2Edt by lazy { spf2.edit() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnLogin.setOnClickListener { clickLogin() }

    }

    private fun clickLogin() {
        val email = binding.inputLayoutEmail.editText!!.text.toString()
        val password = binding.inputLayoutPassword.editText!!.text.toString()
        var nickname = ""

        FBAuth.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            val uid = FBAuth.getUid()
            if (it.isSuccessful) {

                FBRef.userRef.whereEqualTo("uid", uid).get().addOnSuccessListener { qs ->
                    for (data in qs) {
                        nickname = data.data["nickname"].toString()
                    }
                    spfEdt.putBoolean("isLogin", true)
                    spf2Edt.putString("uid", uid)
                    spf2Edt.putString("nickname", nickname)
                    spfEdt.apply()
                    spf2Edt.apply()
                    G.uid = uid
                    G.nickname = nickname
//                    AlertDialog.Builder(this).setMessage("${nickname}").create().show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}