package com.weare2024.tonight.activites

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import androidx.viewpager2.widget.ViewPager2
import com.weare2024.tonight.adapter.PagerAdapter
import com.weare2024.tonight.databinding.ActivityNewPostBinding
import com.weare2024.tonight.network.RetrofitHelper
import com.weare2024.tonight.network.RetrofitService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.StringBuilder

class NewPostActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNewPostBinding.inflate(layoutInflater) }
    private val boardList = mutableMapOf<String, String>()
    private var imgPath: String? = null
    private var imgPath2 = mutableListOf<String>()
    private val files = mutableListOf<MultipartBody.Part>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.buttonPost.setOnClickListener {
            insertData()
//            Toast.makeText(this, "새 게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.ivPost.setOnClickListener { imageUpload() }
    }

    val imgs: MutableList<Uri?> = mutableListOf()
    val pager: ViewPager2 by lazy { binding.pager }

    private fun insertData() {
        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
        val retrofitService = retrofit.create(RetrofitService::class.java)

        boardList["uid"] = "uid"
        boardList["nickname"] = "nickname"
        boardList["content"] = binding.textPost.text.toString()

        for (i in 0 until imgPath2.size) {
            val file = File(imgPath2[i])
            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

            val filePart = MultipartBody.Part.createFormData("img[]", file.name, requestBody)
            files.add(filePart)

        }

        retrofitService.insertBoard(boardList, files).enqueue(object : Callback<String> {
            override fun onResponse(p0: Call<String>, p1: Response<String>) {
                val s = p1.body()
//                AlertDialog.Builder(this@NewPostActivity).setMessage("$s").create().show()
                finish()
            }

            override fun onFailure(p0: Call<String>, p1: Throwable) {
                Log.d("aaaa", "${p1.message}")
            }

        })
    }

    private fun imageUpload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pickMultipleLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else resultLauncher.launch(
            Intent(Intent.ACTION_OPEN_DOCUMENT).setType("image/*")
                .putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        )
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.data?.data != null) {
                imgs.add(result.data?.data)
                imgPath = getRealPathFromUri(result.data?.data!!)
                imgPath2.add(imgPath!!)
            } else {
                val cnt: Int = result.data?.clipData?.itemCount!!
                for (i in 0 until cnt) {
                    imgs.add(result.data?.clipData?.getItemAt(i)?.uri)
                    imgPath = getRealPathFromUri(result.data?.clipData?.getItemAt(i)?.uri!!)
                    imgPath2.add(imgPath!!)
                }
                binding.ivPost.visibility = View.GONE
                pager.visibility = View.VISIBLE
                pager.adapter = PagerAdapter(this, imgs)
            }
        }

    private val pickMultipleLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {

                uris: List<Uri> ->

            if (uris.isNotEmpty()) {
                binding.ivPost.visibility = View.GONE
                pager.visibility = View.VISIBLE
                for (uri in uris) {
                    imgs.add(uri)
                    imgPath = getRealPathFromUri(uri)
                    imgPath2.add(imgPath!!)
                }
                pager.adapter = PagerAdapter(this, imgs)
            }

        }

    private fun getRealPathFromUri(uri: Uri): String? {
        //android 10 버전 부터는 Uri를 통해 파일의 실제 경로를 얻을 수 있는 방법이 없어졌음
        //그래서 uri에 해당하는 파일을 복사하여 임시로 파일을 만들고 그 파일의 경로를 이용하여 서버에 전송

        //Uri[미디어저장소의 DB 주소]로 부터 파일의 이름을 얻어오기 - DB SELECT 쿼리작업을 해주는 기능을 가진 객체를 이용
        val cursorLoader: CursorLoader = CursorLoader(this, uri, null, null, null, null)
        val cursor: Cursor? = cursorLoader.loadInBackground()
        val fileName: String? = cursor?.run {
            moveToFirst()
            getString(getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
        }

        //복사본이 저장될 파일의 경로와 파일명.확장자
        val file: File = File(externalCacheDir, fileName.toString())

        //이제 복사 작업 수행
        val inputStream: InputStream = contentResolver.openInputStream(uri) ?: return null
        val outputStream: OutputStream = FileOutputStream(file)

        //파일복사
        while (true) {
            val buf: ByteArray = ByteArray(1024) //빈 바이트 배열 [길이:1KB]
            val len: Int = inputStream.read(buf) //스트림을 통해 읽어들인 바이트들을 buf 배열에 넣어줌
            if (len <= 0) break

            outputStream.write(buf, 0, len)
        }

        //반복문이 끝났으면 복사가 완료된 것임za
        inputStream.close()
        outputStream.close()

        return file.absolutePath
    }
}
