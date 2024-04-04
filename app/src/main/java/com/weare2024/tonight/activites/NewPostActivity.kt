package com.weare2024.tonight.activites

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.weare2024.tonight.R
import com.weare2024.tonight.adapter.PagerAdapter
import com.weare2024.tonight.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewPostBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.buttonPost.setOnClickListener {
            Toast.makeText(this, "새 게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.ivPost.setOnClickListener { imageUpload() }
    }

    val imgs: MutableList<Uri?> = mutableListOf()
    val pager: ViewPager2 by lazy { binding.pager }

    private fun imageUpload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pickMultipleLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        else resultLauncher.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*").putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true))
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->

            if (result.data?. data != null) {
                imgs.add(result.data?.data)
            } else {
                val cnt:Int = result.data?.clipData?.itemCount!!
                for (i in 0 until cnt) {
                    imgs.add(result.data?.clipData?.getItemAt(i)?.uri)
                }

            }
            binding.ivPost.visibility = View.GONE
            pager.visibility = View.VISIBLE
            pager.adapter= PagerAdapter(this, imgs)
    }

    private val pickMultipleLauncher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()){

        uris: List<Uri> ->

        if (uris.isNotEmpty()) {
            binding.ivPost.visibility = View.GONE
            pager.visibility = View.VISIBLE
            for (uri in uris) imgs.add(uri)
            pager.adapter= PagerAdapter(this, imgs)
        }

    }
}
