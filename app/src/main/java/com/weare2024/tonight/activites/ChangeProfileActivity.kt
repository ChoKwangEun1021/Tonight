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
import com.google.firebase.firestore.SetOptions
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
        binding.btn.setOnClickListener { clickBtn() }
        binding.iv.setOnClickListener { getImage() }

    }
    private fun clickRegister() {
        val uid = G.uid

        if (uid.isNotEmpty()) {
            // 사용자의 UID로 해당 사용자를 식별하여 Firestore에서 사용자 문서 가져오기
            FBRef.userRef.whereEqualTo("uid", uid).get().addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val newNickname = binding.inputLayoutNickName.editText!!.text.toString()
                    // 사용자의 닉네임 업데이트
                    val newData = mutableMapOf<String, Any>() // 이거 한 이유 빈 거 만들어서 데이터 담을것
                    newData["nickname"] = newNickname
                    // 사용자 문서 업데이트
                    document.reference.set(newData, SetOptions.merge()).addOnSuccessListener {
                        Toast.makeText(this, "닉네임이 성공적으로 업데이트되었습니다.", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "닉네임 업데이트에 실패했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "사용자 문서를 가져오는 데 실패했습니다: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clickBtn() {
        if (binding.iv.drawable is VectorDrawable) {
            Toast.makeText(this, "사진을 선택해 주세요", Toast.LENGTH_SHORT).show()
            return
        } else if (binding.inputLayoutNickName.editText!!.text.toString().isEmpty()) {
            Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        } else {
            Toast.makeText(this, "사진을 선택해 주세요", Toast.LENGTH_SHORT).show()
        }
        clickRegister()
    }

    private fun getImage() {
        val intent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Intent(MediaStore.ACTION_PICK_IMAGES) else Intent(
                Intent.ACTION_OPEN_DOCUMENT
            ).setType("image")
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                imgUri = result.data?.data
                Glide.with(this).load(imgUri).into(binding.iv)
            }
        }
}