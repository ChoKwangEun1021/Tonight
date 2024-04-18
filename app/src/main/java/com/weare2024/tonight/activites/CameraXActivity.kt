package com.weare2024.tonight.activites

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityCameraXactivityBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CameraXActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCameraXactivityBinding.inflate(layoutInflater) }
    val previewView: PreviewView by lazy { findViewById(R.id.preview_view) }
    var imageCapture:ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.floationgBtn.setOnClickListener { takeBtn() }

        val permissionState:Int= checkSelfPermission(Manifest.permission.CAMERA)
        if(permissionState== PackageManager.PERMISSION_DENIED){
            //퍼미션 요청 다이얼로 보여주는 작업을 대신해주는 대행사 이용
            resultLauncher.launch(Manifest.permission.CAMERA)
        }else{

        }

        }


    private fun takeBtn(){


    }

    private fun takePhoto(){
        // 이미지캡처 객체가 없을 수도 있으니..
        imageCapture ?: return

        //촬영한 사진이 저장될 파일명
        val name:String = SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA).format(System.currentTimeMillis()) // "2024.02.20 11:52:32"
        //MediStore의 Database에 파일의 정보를 삽입(insert) 하기 위해.. 한줄(레코드) 데이터 객체 생성
        val contentValues: ContentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name) //파일명
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg") //타입
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.P){
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,"Pictures/Ex56") //경로
        }

//        //촬영한 사진을 저장할 옵션객체
//        val outputFileOptions: ImageCapture.OutputFileOptions = ImageCapture.OutputFileOptions.Builder(contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues).build()
//
//        imageCapture!!.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), object :
//            ImageCapture.OnImageSavedCallback {
//            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                Snackbar.make(previewView,"image saved", Snackbar.LENGTH_SHORT).show()
//
//                //촬영된 사진 uri
//                val uri: Uri? = outputFileResults.savedUri
//                Glide.with(this@CameraXActivity).load(uri).into(iv)
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                Toast.makeText(this@CameraXActivity, "fail", Toast.LENGTH_SHORT).show()
//            }
//        })

    }

    private fun startPreview(){
        Toast.makeText(this, "미리보기를 시작합니다.", Toast.LENGTH_SHORT).show()

        //카메라 프로세스 제공객체 참조하기
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        //카메라 프로세스 작업 준비가 완료되었다는 것을 듣는 리스너 처리
        cameraProviderFuture.addListener({
            //카메라 프로세스 시작!
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            //프리뷰 객체 생성
            val preview= Preview.Builder().build()
            //프리뷰가 취득한 이미지데이터를 보여줄 고속버퍼뷰(Surface)를 설정
            preview.setSurfaceProvider( previewView.surfaceProvider )

            // 프리뷰 작업 시작..
            cameraProvider.unbindAll()
            //cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview)

            //이미지 캡처가 포함된 작업 시작
            imageCapture= ImageCapture.Builder().build()
            cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture)


        }, ContextCompat.getMainExecutor(this))

    }

    val resultLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
            permissionResult:Boolean ->

        if(permissionResult) {
            //카메라 미리보기를 시작!!

        }
        else finish()
    }
}