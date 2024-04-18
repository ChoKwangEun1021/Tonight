package com.weare2024.tonight.activites

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.weare2024.tonight.G
import com.weare2024.tonight.databinding.ActivitySignup2Binding
import com.weare2024.tonight.firebase.FBAuth
import com.weare2024.tonight.firebase.FBRef

class SignupActivity2 : AppCompatActivity() {
    private val binding by lazy { ActivitySignup2Binding.inflate(layoutInflater) }
    private var imgUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { clickImage() }
        binding.cvProfile.setOnClickListener { getImage() }
    }

    private fun clickRegister() {
        val nickname = binding.inputLayoutNickName.editText!!.text.toString()

        if (intent != null && intent.hasExtra("login_type")) {
            when (intent.getStringExtra("login_type")) {

                "kakao" -> {

                    val uid = intent.getStringExtra("kakao_uid")
                    val intent = Intent(this, MyProfileActivity1::class.java)
                    Log.d("이미지", "$imgUri")
                    intent.putExtra("kakao_uid", uid)
                    intent.putExtra("nickname", nickname)
                    intent.putExtra("profileImgUri", imgUri.toString())
                    intent.putExtra("login_type", "kakao")
                    startActivity(intent)
                    Log.d("aaaa", "$imgUri")

                }

                "naver" -> {
                    val uid = intent.getStringExtra("naver_uid")
                    val naverEmail = intent.getStringExtra("naver_email")
                    val intent = Intent(this, MyProfileActivity1::class.java)
                    intent.putExtra("naver_uid", uid)
                    intent.putExtra("naver_email", naverEmail)
                    intent.putExtra("profileImgUri", imgUri.toString())
                    intent.putExtra("nickname", nickname)
                    intent.putExtra("login_type", "naver")
                    startActivity(intent)
//                    Toast.makeText(this, $uid, Toast.LENGTH_SHORT).show()

                }

                "google" -> {
                    val uid = intent.getStringExtra("google_uid")
                    val googleEmail = intent.getStringExtra("google_email")
                    val intent = Intent(this, MyProfileActivity1::class.java)
                    intent.putExtra("google_uid", uid)
                    intent.putExtra("nickname", nickname)
                    intent.putExtra("google_email", googleEmail)
                    intent.putExtra("profileImgUri", imgUri.toString())
                    intent.putExtra("login_type", "google")
                    startActivity(intent)
                    Toast.makeText(this, "$uid,$nickname", Toast.LENGTH_SHORT).show()
                }

                "email" -> {
                    val nickname = binding.inputLayoutNickName.editText!!.text.toString()
                    val password = intent.getStringExtra("password").toString()
                    val email = intent.getStringExtra("email").toString()
                    val intent = Intent(this, MyProfileActivity1::class.java)

                    intent.putExtra("email", email)
                    intent.putExtra("password", password)
                    intent.putExtra("nickname", nickname)
                    intent.putExtra("profileImgUri", imgUri.toString())
                    intent.putExtra("login_type", "email")
                    startActivity(intent)

//                    AlertDialog.Builder(this).setMessage("$email $password $nickname $uid")

                }
            }

        }


    }

    private fun clickImage() {
        if (binding.cvProfile.drawable is VectorDrawable) {
            Toast.makeText(this, "사진을 선택해 주세요", Toast.LENGTH_SHORT).show()
            return
        } else if (binding.inputLayoutNickName.editText!!.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        } else {
            Toast.makeText(this, "이미지 선택 완료", Toast.LENGTH_SHORT).show()
            clickRegister()
        }
    }

    private fun getImage() {
        val intent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Intent(MediaStore.ACTION_PICK_IMAGES) else Intent(
                Intent.ACTION_OPEN_DOCUMENT
            ).setType("image/*")
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                imgUri = it.data?.data
                Glide.with(this).load(imgUri).into(binding.cvProfile)
            }
        }
}