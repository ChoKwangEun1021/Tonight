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
    private val spf by lazy { getSharedPreferences("loginSave", MODE_PRIVATE) }
    private val spf2 by lazy { getSharedPreferences("userInfo", MODE_PRIVATE) }
    private val spfEdt by lazy { spf.edit() }
    private val spf2Edt by lazy { spf2.edit() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { clickImage() }
        binding.cvProfile.setOnClickListener { getImage() }
    }

    private fun clickRegister() {
        val email = intent.getStringExtra("email").toString()
        val password = intent.getStringExtra("password").toString()
        val nickname = binding.inputLayoutNickName.editText!!.text.toString()

        if (intent != null && intent.hasExtra("login_type")) {
            when(intent.getStringExtra("login_type")) {

                "kakao" -> {

                    val uid = intent.getStringExtra("kakao_uid")
                    val intent = Intent(this, MyProfileActivity1::class.java)
                    intent.putExtra("kakao_uid", uid)
                    intent.putExtra("nickname", nickname)
                    intent.putExtra("profileImgUri", imgUri)
                    intent.putExtra("login_type", "kakao")
                    startActivity(intent)
                    Toast.makeText(this, "$uid,", Toast.LENGTH_SHORT).show()


                }

                "naver" -> {

                }

                "google" -> {
                    val uid = intent.getStringExtra("google_uid")
                    val googleEmail = intent.getStringExtra("google_email")
                    val intent = Intent(this,MyProfileActivity1::class.java)
                    intent.putExtra("google_uid",uid)
                    intent.putExtra("nickname",nickname)
                    intent.putExtra("google_email",googleEmail)
                    intent.putExtra("login_type", "google")
                    startActivity(intent)
                    Toast.makeText(this, "$uid,$nickname", Toast.LENGTH_SHORT).show()
                }

                "email" -> {

                    FBAuth.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        val uid = FBAuth.getUid()
                        val users = mutableMapOf<String, String>()

                        if (it.isSuccessful) {
                            FBRef.userRef.whereEqualTo("email", email).get().addOnSuccessListener {
                                users["uid"] = uid
                                users["email"] = email
                                users["nickname"] = nickname
                                users["profileImgUri"] = uid
                                users["gender"] = "남자"
                                users["height"] = "187cm"
                                users["birth"] = "1998.10.21"
                                users["area"] = "서울"
                                users["work"] = "개발자"

                                //자동 로그인 기능
                                spfEdt.putBoolean("isLogin", true)
                                spf2Edt.putString("kakao_uid", uid)
                                spf2Edt.putString("nickname",nickname)
                                spfEdt.apply()
                                spf2Edt.apply()

                                FBRef.userRef.document(nickname).set(users).addOnSuccessListener {
                                    Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                }
                                FBAuth.auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()


                                }
                                userProfileImgUpload()
                                startActivity(Intent(this, MainActivity::class.java))
                            }

                        } else {
                            Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }


                    }
                }
            }

        }



    }

    private fun clickImage(){
        if (binding.cvProfile.drawable is VectorDrawable){
            Toast.makeText(this, "사진을 선택해 주세요", Toast.LENGTH_SHORT).show()
            return
        }else if (binding.inputLayoutNickName.editText!!.text.toString().isNullOrEmpty() ){
            Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }else{
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

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            imgUri = it.data?.data
            Glide.with(this).load(imgUri).into(binding.cvProfile)
        }
    }

    private fun userProfileImgUpload() {
        var name = ""

        if (intent != null && intent.hasExtra("login_type")) {
            when (intent.getStringExtra("login_type")) {
                "kakao" -> {
                }

                "naver" -> {
                }

                "google" -> {
                }

                "email" -> {
                    name = FBAuth.getUid()

                    val imgRef: StorageReference = Firebase.storage.getReference("usersImg/$name")

                    imgUri?.apply {
                        imgRef.putFile(this).addOnSuccessListener {
                            Log.d("img upload", "이미지 업로드 성공")
                        }
                    }
                }

            }
        }

    }
}