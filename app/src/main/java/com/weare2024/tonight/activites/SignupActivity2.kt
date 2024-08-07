package com.weare2024.tonight.activites

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.weare2024.tonight.G
import com.weare2024.tonight.databinding.ActivitySignup2Binding
import com.weare2024.tonight.firebase.FBRef
import com.weare2024.tonight.ml.Modelperson
import org.tensorflow.lite.support.image.TensorImage

class SignupActivity2 : AppCompatActivity() {
    private val binding by lazy { ActivitySignup2Binding.inflate(layoutInflater) }
    private var imgUri: Uri? = null
    var aa = 0
    var asa = 0

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

                }
            }
        }
    }

    private fun clickImage() {
        if (asa ==1){

            mlKit()
            if (binding.cvProfile.drawable is VectorDrawable) {
                Toast.makeText(this, "사진을 선택해 주세요", Toast.LENGTH_SHORT).show()
                return
            } else if (binding.inputLayoutNickName.editText!!.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
                return
            } else if (aa != 0) {
//            Toast.makeText(this, "사람이 아닙니다.", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "$aa", Toast.LENGTH_SHORT).show()
                clickRegister()
            } else if (aa ==0){
                Toast.makeText(this,  "확인버튼을 다시눌러주세요", Toast.LENGTH_SHORT).show()
//            clickRegister()

            }else {
                Toast.makeText(this, "이미지 선택 완료.", Toast.LENGTH_SHORT).show()


            }
        }else{
            Toast.makeText(this@SignupActivity2, "fuckyou", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun getImage() {
        val intent =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Intent(MediaStore.ACTION_PICK_IMAGES) else Intent(
                Intent.ACTION_OPEN_DOCUMENT
            ).setType("image/*")
        asa =1
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                imgUri = it.data?.data
                Glide.with(this).load(imgUri).into(binding.cvProfile)
            }
        }

    private fun MachineLearning() {
        val model: Modelperson = Modelperson.newInstance(this)
        val bm: Bitmap = (binding.cvProfile.drawable as BitmapDrawable).bitmap
        val image: TensorImage = TensorImage.fromBitmap(bm)
        val outputs = model.process(image)
        for (category in outputs.probabilityAsCategoryList) {
            binding.tvTest.append("${category.label} : ${category.displayName} - ${category.score}\n")
        }

    }


    private fun mlKit() {

        val options: FaceDetectorOptions = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE) // 정확도 위주
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL) // 눈, 귀 , 코 , 뺨, 입술 같은 얼굴 특징
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL) // 얼굴 특징의 윤곽을 감지할지 여부
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL) // 얼굴이 "웃고 있음" 또는 "눈을 뜨고 있음" 같은 카테고리를 분류
            .build()
        val faceDetector: FaceDetector = FaceDetection.getClient(options)
        val bm: Bitmap = (binding.cvProfile.drawable as BitmapDrawable).bitmap
        val image: InputImage = InputImage.fromBitmap(bm, 0)

        faceDetector.process(image).addOnSuccessListener {
            binding.tvTest.text = "감지된 얼굴 갯수 ${it.size}\n\n"
            aa = it.size
        }
        AlertDialog.Builder(this).setMessage("$aa").create().show()
    }

}