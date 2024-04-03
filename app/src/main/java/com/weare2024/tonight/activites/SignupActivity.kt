package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.weare2024.tonight.databinding.ActivitySignupBinding
import com.weare2024.tonight.firebase.FBAuth
import com.weare2024.tonight.firebase.FBRef

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var passwordConfirm: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.btnNext.setOnClickListener { clickNext() }
    }

    private fun clickNext() {
        val intent = Intent(this, MainActivity::class.java)

        email = binding.inputLayoutEmail.editText!!.text.toString()
        password = binding.inputLayoutPassword.editText!!.text.toString()
        passwordConfirm = binding.inputLayoutPasswordConfirm.editText!!.text.toString()

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "이메일 또는 비밀번호를 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
        } else if (password != passwordConfirm) {
            AlertDialog.Builder(this).setMessage("비밀번호가 같지 않습니다 다시 입력해주세요.").create().show()
            binding.inputLayoutPasswordConfirm.requestFocus()
            return
        } else {
            FBAuth.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    FBRef.userRef.whereEqualTo("email", email).get().addOnSuccessListener {
                        val uid = FBAuth.getUid()
                        val users = mutableMapOf<String, String>()
                        users["uid"] = uid
                        users["email"] = email
                        users["password"] = password
                        users["nickName"] = "nickName"

                        FBRef.userRef.document().set(users).addOnSuccessListener {
                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        }
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }

            }
//            intent.putExtra("email", email)
//            intent.putExtra("password", password)
//            intent.putExtra("login_type", "email")

        }
    }
}