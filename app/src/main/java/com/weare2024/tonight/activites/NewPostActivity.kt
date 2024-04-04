package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityNewPostBinding
import com.weare2024.tonight.network.RetrofitHelper

class NewPostActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewPostBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.buttonPost.setOnClickListener {
            insertData()
//            Toast.makeText(this, "새 게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.ivPost.setOnClickListener { imagePost() }
    }

    private fun insertData() {
        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
    }

    private fun imagePost() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) resultLauncher.launch(
            Intent(
                MediaStore.ACTION_PICK_IMAGES
            )
        )
        else resultLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*"))
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val uri = it.data?.data
                if (uri != null) {
                    binding.ivPost.setImageURI(uri)
                }
            }
        }
}
