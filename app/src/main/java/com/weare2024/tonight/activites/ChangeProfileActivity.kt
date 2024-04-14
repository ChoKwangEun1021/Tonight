package com.weare2024.tonight.activites

import android.content.Intent
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityChangeProfileBinding
import com.weare2024.tonight.firebase.FBAuth
import com.weare2024.tonight.firebase.FBRef

class ChangeProfileActivity : AppCompatActivity() {
    private val binding by lazy { ActivityChangeProfileBinding.inflate(layoutInflater) }
    private var imgUri: Uri? = null
    private val spf2 by lazy { getSharedPreferences("userInfo", MODE_PRIVATE) }
    private val spf2Edt by lazy { spf2.edit() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.btnRegister.setOnClickListener { clickImage() }
        binding.cvProfile.setOnClickListener { getImage() }

    }
    private fun clickRegister() {
//        val user = FirebaseAuth.getInstance().currentUser
//        if (user != null) {
//            Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show()
//            // 사용자가 로그인되어 있음
//        } else {
//            Toast.makeText(this, "퇴장", Toast.LENGTH_SHORT).show()
//
//
//            // 사용자가 로그인되어 있지 않음
//        }
//        val uid = FirebaseAuth.getInstance().currentUser?.uid
//        if (uid != null) {
//            // 올바른 UID를 가져왔음
//            Log.d("UID", "사용자의 UID: $uid")
//        } else {
//            // UID가 null임
//            Log.d("UID", "사용자의 UID를 가져올 수 없음")
//        }
        val nickname = FBAuth.getUid()
        if (nickname.isNotEmpty()) {
            val userDocRef = FBRef.userRef.document(nickname)
            userDocRef.get()
                .addOnSuccessListener { document ->
                    // 사용자의 기존 닉네임을 가져와서 토스트로 표시합니다.
                    val oldNickname = document.getString("nickname")
                    Toast.makeText(this@ChangeProfileActivity, "nickname : $oldNickname", Toast.LENGTH_SHORT).show()
                }
//                    if (document.exists()) {
//
//                        // 사용자가 입력한 새로운 닉네임
//                        val newNickname = binding.inputLayoutNickName.editText?.text.toString()
//                        // 새로운 닉네임으로 업데이트
//                        userDocRef.update("nickname", newNickname)
//                            .addOnSuccessListener {
//                                // 업데이트 성공
//                                Toast.makeText(this, "프로필이 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT)
//                                    .show()
//                                // G 클래스의 nickname 업데이트
//                                G.nickname = newNickname
//                            }
//                            .addOnFailureListener { e ->
//                                // 업데이트 실패
//                                Toast.makeText(this, "프로필 변경에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                                Log.e("Firestore", "프로필 변경 실패: ${e.message}", e)
//                            }
//                    } else {
//                        // 해당 UID를 가진 문서가 없는 경우 처리할 내용을 여기에 추가합니다.
//                        Toast.makeText(this, "해당 사용자의 정보가 없습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                .addOnFailureListener { e ->
//                    // 문서를 가져오는데 실패한 경우 처리할 내용을 여기에 추가합니다.
//                    Toast.makeText(this, "사용자 정보를 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    Log.e("Firestore", "사용자 정보 가져오기 실패: ${e.message}", e)
//                }
//        } else {
//            // 사용자의 UID가 없는 경우 처리할 내용을 여기에 추가합니다.
//            Toast.makeText(this, "사용자 식별 정보가 없습니다.", Toast.LENGTH_SHORT).show()
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
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                imgUri = result.data?.data
                Glide.with(this).load(imgUri).into(binding.cvProfile)
            }
        }
}