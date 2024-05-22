package com.weare2024.tonight.activites

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityCameraXactivityBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.weare2024.tonight.MyOverlayView

class CameraXActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCameraXactivityBinding.inflate(layoutInflater) }
    val previewView: PreviewView by lazy { findViewById(R.id.preview_view) }
    var imageCapture: ImageCapture? = null

    var gendera = ""
    var cameraGendera = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        gendera = intent.getStringExtra("gender").toString()
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else startCameraPreview()
        binding.btn.setOnClickListener { takeBtn() }
        binding.btn.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        } else startCameraPreview()
        }

//        val permissionState: Int = checkSelfPermission(Manifest.permission.CAMERA)

    }

    val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) startCameraPreview()// 카메라 프리뷰 시작
            else Toast.makeText(this, "카메라 혀용 해야만 사용 가능", Toast.LENGTH_SHORT).show()
        }

    private fun startCameraPreview() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            //STRP 1.) Preview 객체생성
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.previewView.surfaceProvider)
            //STRP 2.) imageAnalysis 객체생성 -imageAnalysis.Analyzer 인터페이스를 구현한 클래스로 객체를 생성
            val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()
            // 이미지 분석에  사용할 이미지 분석가 설정
            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), MyImageAnalyzer())

            //카메라선택
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.unbindAll()
            // 멕티비티의 라이프사이클에 연결된 카메라 작업들(프리뷰,캡쳐,분석,비디오촬영 등 의작업)
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        }, ContextCompat.getMainExecutor(this))
        //이미지 분석가 이너 클래스 설계

    }

    inner class MyImageAnalyzer : ImageAnalysis.Analyzer {
        // 2 Object Detector 객체 생성
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableMultipleObjects()
            .build()
        val detectorOptions = ObjectDetection.getClient(options)

        @OptIn(ExperimentalGetImage::class)
        override fun analyze(image: ImageProxy) {
            Log.d("Tag", "MyImageAnalyzer")
            //Mlkit 오브젝트 디텍션 작업 시작 현재 이미지에 대해서
            val mediaImage = image.image
            if (mediaImage != null) {
                val inputImage = InputImage.fromMediaImage(
                    mediaImage,
                    image.imageInfo.rotationDegrees
                )// 프레임의 이미지 회전각도를 반영
                // 이미지처리
                detectorOptions.process(inputImage).addOnSuccessListener {
                    binding.tv.text = "탐지된 객체 개수 ${it.size}\n\n"
                    for (de in it) {
                        binding.tv.append("${de.trackingId} : ${de.boundingBox}\n")
                    }
                    //camerax 의 이미지분석용 image 는 무조건 w:640 H:480
                    //이미지와 오버레이뷰의 크기 비율 계산 이거 잘못되고있음
                    val hightRatio: Double =
                        binding.myOverlayView.width.toDouble() / image.width.toDouble()
                    val widthRatio: Double =
                        binding.myOverlayView.height.toDouble() / image.width.toDouble()
                    binding.myOverlayView.drawObjectBoundingBox(it, hightRatio, widthRatio)
                    //현재이미지에 대한 분석이 끝났다면 닫아줘야함 닫으면 다음이미지 분석을 위해 또 이 analyze 메소드가 실행됨
                    image.close()
                }
            }
        }
    }

    private fun takeBtn() {
        if (intent != null && intent.hasExtra("login_type")) {


            when (intent.getStringExtra("login_type")) {


                "kakao" -> {
                    if (gendera == cameraGendera) {

                        val uid = intent.getStringExtra("kakao_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity2::class.java
                        )

                        intent.putExtra("gender", cameraGendera)
                        intent.putExtra("kakao_uid", uid)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("profileImgUri", imguri)
                        intent.putExtra("login_type", "kakao")
                        startActivity(intent)

                        intent.putExtra("gender", cameraGendera)

                        startActivity(intent)
                    } else if (gendera != cameraGendera) {
                        val uid = intent.getStringExtra("kakao_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity1::class.java
                        )
                        intent.putExtra("kakao_uid", uid)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("profileImgUri", imguri)
                        intent.putExtra("login_type", "kakao")
                        Toast.makeText(this, "선택하신 성별과 다릅니다", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }

                }

                "naver" -> {
                    if (gendera == cameraGendera) {
                        val uid = intent.getStringExtra("naver_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val email = intent.getStringExtra("naver_email")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity2::class.java
                        )


                        intent.putExtra("gender", cameraGendera)
                        intent.putExtra("naver_uid", uid)
                        intent.putExtra("naver_email", email)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("login_type", "naver")
                        intent.putExtra("profileImgUri", imguri)
                        startActivity(intent)
                    } else {
                        val uid = intent.getStringExtra("naver_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val email = intent.getStringExtra("naver_email")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity1::class.java
                        )
                        intent.putExtra("naver_uid", uid)
                        intent.putExtra("naver_email", email)
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("login_type", "naver")
                        intent.putExtra("profileImgUri", imguri)
                        Toast.makeText(this, "선택하신 성별과 다릅니다", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }

                "google" -> {
                    if (gendera == cameraGendera) {
                        val googleEmail = intent.getStringExtra("google_email")
                        val uid = intent.getStringExtra("google_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity2::class.java
                        )

                        intent.putExtra("gender", cameraGendera)
                        intent.putExtra("google_uid", uid)
                        intent.putExtra("login_type", "google")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("google_email", googleEmail)
                        intent.putExtra("profileImgUri", imguri)
                        startActivity(intent)

                    } else {
                        val googleEmail = intent.getStringExtra("google_email")
                        val uid = intent.getStringExtra("google_uid")
                        val nickname = intent.getStringExtra("nickname")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity1::class.java
                        )
                        intent.putExtra("google_uid", uid)
                        intent.putExtra("login_type", "google")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("google_email", googleEmail)
                        intent.putExtra("profileImgUri", imguri)
                        Toast.makeText(this, "선택하신 성별과 다릅니다", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }

                "email" -> {
                    if (gendera == cameraGendera) {
                        val email = intent.getStringExtra("email")
                        val nickname = intent.getStringExtra("nickname")
                        val password = intent.getStringExtra("password")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity2::class.java
                        )

                        intent.putExtra("gender", cameraGendera)
                        intent.putExtra("login_type", "email")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        intent.putExtra("profileImgUri", imguri)
                        startActivity(intent)
                    } else {
                        val email = intent.getStringExtra("email")
                        val nickname = intent.getStringExtra("nickname")
                        val password = intent.getStringExtra("password")
                        val imguri = intent.getStringExtra("profileImgUri")
                        val intent = Intent(
                            this,
                            MyProfileActivity1::class.java
                        )
                        intent.putExtra("login_type", "email")
                        intent.putExtra("nickname", nickname)
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        intent.putExtra("profileImgUri", imguri)
                        Toast.makeText(this, "선택하신 성별과 다릅니다", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                }
            }

        }
    }
}

