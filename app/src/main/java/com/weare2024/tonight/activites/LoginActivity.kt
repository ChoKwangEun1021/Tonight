package com.weare2024.tonight.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.weare2024.tonight.data.NaverLogin
import com.weare2024.tonight.firebase.FBRef
import com.weare2024.tonight.network.RetrofitHelper
import com.weare2024.tonight.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.kakao.sdk.auth.AuthApiClient
import com.weare2024.tonight.data.UserData


class LoginActivity : AppCompatActivity(), OnClickListener {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val spf by lazy { getSharedPreferences("loginSave", MODE_PRIVATE) }
    private val spf2 by lazy { getSharedPreferences("userInfo", MODE_PRIVATE) }
    private val spfEdt by lazy { spf.edit() }
    private val spf2Edt by lazy { spf2.edit() }
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (spf.getBoolean("isLogin", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnGo.setOnClickListener(this)
        binding.btnLoginKakao.setOnClickListener(this)
        binding.btnLoginNaver.setOnClickListener(this)
        binding.btnLoginGoogle.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.btnSignup.setOnClickListener(this)
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_go -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            R.id.btn_login_kakao -> {
                clickKakao()
            }

            R.id.btn_login_naver -> {
//                naver()
                naverBYheesoo()

            }
            R.id.btn_login_google -> {
                google()
            }

            R.id.btn_login -> {
                startActivity(Intent(this, EmailLoginActivity::class.java))
            }

            R.id.btn_signup -> {
                spfEdt.putBoolean("isLogin", true)
                spfEdt.apply()
                val intent = Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }
    fun naverBYheesoo(){
        //초기화
        NaverIdLoginSDK.initialize(this, getString(R.string.client_id), getString(R.string.client_secret), "Tonight")

        //로그인 요청
        NaverIdLoginSDK.authenticate(this, object : OAuthLoginCallback{
            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(this@LoginActivity, "네이버 로그인 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Toast.makeText(this@LoginActivity, "네이버 로그인 실패", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                //로그인 성공시 REST API로 사용자 정보 받아오기
                Toast.makeText(this@LoginActivity, "네이버로 로그인 합니다.", Toast.LENGTH_SHORT).show()
                val accessToken:String? = NaverIdLoginSDK.getAccessToken()

                val retrofit= RetrofitHelper.getRetrofitInstance("https://openapi.naver.com")
                val retrofitApiService= retrofit.create(RetrofitService::class.java)
                val call= retrofitApiService.getNidUserInfo("Bearer $accessToken")
                call.enqueue(object : Callback<NaverLogin>{
                    override fun onResponse(p0: Call<NaverLogin>, p1: Response<NaverLogin>) {

                        val s= p1.body()
                        val intent= Intent(this@LoginActivity, SignupActivity2::class.java)
                        val uid= s?.response?.id
                        val email= s?.response?.email

                        intent.putExtra("naver_uid", uid)
                        intent.putExtra("naver_email", email)
                        intent.putExtra("login_type", "naver")

                        startActivity(intent)
                        finish()
                    }

                    override fun onFailure(p0: Call<NaverLogin>, p1: Throwable) {
                        Toast.makeText(this@LoginActivity, "네이버 로그인 실패", Toast.LENGTH_SHORT).show()
                    }

                })


            }

        })
    }
//    private fun naver() {
//        //네아로 SDK 초기화
//        NaverIdLoginSDK.initialize(
//            this,
//            getString(R.string.client_id),
//            getString(R.string.client_secret),
//            "Tonight"
//        )
//
//        val account = NaverIdLoginSDK.getAccessToken()
//
//        if (account != null) {
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        } else {
//            //로그인 요청
//            NaverIdLoginSDK.authenticate(this, object : OAuthLoginCallback {
//                override fun onError(errorCode: Int, message: String) {
//                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onFailure(httpStatus: Int, message: String) {
//                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
//                }
//                override fun onSuccess() {
//                    Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
//
//                    //사용자 정보를 받아오기 -- REST API로 받아야 함
//                    //로그인에 성공하면 REST API로 요청할 수 있는 토큰(token)을 발급받음
//                    val accessToken: String? = NaverIdLoginSDK.getAccessToken()
//
//                    //Retrofit 작업을 통해 사용자 정보 가져오기
//                    val retroift =
//                        RetrofitHelper.getRetrofitInstance("https://openapi.naver.com")
//                    val retrofitApiService = retroift.create(RetrofitService::class.java)
//
//                    val call = retrofitApiService.getNidUserInfo("Bearer $accessToken")
//                    call.enqueue(object : Callback<NaverLogin> {
//                        override fun onResponse(
//                            call: Call<NaverLogin>,
//                            response: Response<NaverLogin>
//                        ) {
//                            val s = response.body()
//                            val id = s?.response?.id
//                            val email = s?.response?.email
//
//                            FBRef.userRef.whereEqualTo("uid", id).get().addOnSuccessListener {
//                                if (id != null) {
//                                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                                } else {
//                                    val intent2 = Intent(this@LoginActivity, SignupActivity2::class.java)
//                                    intent2.putExtra("naver_uid", s?.response?.id)
//                                    intent2.putExtra("naver_email", email)
//                                    intent2.putExtra("login_type", "naver")
//
//                                    startActivity(intent)
//                                }
//                            }
//                        }
//                        override fun onFailure(call: Call<NaverLogin>, t: Throwable) {
//                            Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
//                        }
//
//                    })
//                }
//            })
//        }
//    }

    fun google() {
        val signInOptions: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .build()
        val googleToken = GoogleSignIn.getLastSignedInAccount(this)
        if (googleToken == null) {

            val intent = GoogleSignIn.getClient(this, signInOptions).signInIntent
            resultLauncher.launch(intent)

        } else /*("토큰이 있으면")*/ {
            val intent = (Intent(this, MainActivity::class.java))
            requestGoogleLauncher.launch(intent)
        }
    }
    val requestGoogleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intent = it.data
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(intent)

            val account = task.result
            val uid = account.id.toString()
            val googleEmail = account.email ?: "email 이 null임!!! 이거 오류임!!!"
            Toast.makeText(this@LoginActivity, "$googleEmail", Toast.LENGTH_SHORT).show()

            FBRef.userRef.whereEqualTo("uid", uid).get().addOnSuccessListener {
                for (snap in it) {
                    val userData = snap.toObject(UserData::class.java)
                    userData?.apply {
                        G.uid = uid
                        G.nickname = "$nickname"
                        spfEdt.putBoolean("isLogin", true)
                        spf2Edt.putString("uid", uid)
                        spf2Edt.putString("nickname", nickname)
                        spfEdt.apply()
                        spf2Edt.apply()
                        Toast.makeText(this@LoginActivity, "${G.nickname}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intent = it.data

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

            val account = task.result
            val uid = account.id.toString()
            val googleEmail = account.email ?: "email 이 null임!!! 이거 오류임!!!"
            val intent1 = Intent(this, SignupActivity2::class.java)
            intent1.putExtra("google_uid", uid)
            intent1.putExtra("google_email", googleEmail)
            intent1.putExtra("login_type", "google")
            spfEdt.putBoolean("isLogin", true)
            spfEdt.apply()
            startActivity(intent1)
            finish()
        }

    fun clickKakao() {
        val kakaoToken = AuthApiClient.instance.hasToken()
        if (kakaoToken == true) {

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Toast.makeText(this, "카카오로그인 실패", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "카카오로그인 성공", Toast.LENGTH_SHORT).show()

                    // 사용자 정보 요청
                    UserApiClient.instance.me { user, error ->
                        if (user != null) {
                            val uid: String = user.id.toString()

                            FBRef.userRef.whereEqualTo("uid", uid).get().addOnSuccessListener {
                                for (snap in it) {
                                    val userData = snap.toObject(UserData::class.java)

                                    userData?.apply {
                                        G.uid = uid
                                        G.nickname = nickname
                                        spfEdt.putBoolean("isLogin", true)
                                        spf2Edt.putString("uid", uid)
                                        spf2Edt.putString("nickname", nickname)
                                        spfEdt.apply()
                                        spf2Edt.apply()

                                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                        Toast.makeText(this@LoginActivity, "${G.nickname}", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }

                        }
                    }
                }
            }
            if (AuthApiClient.instance.hasToken()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }

        } else /*("토큰이 없으면")*/ {
            // 두개의 로그인 요청 콜백함수
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Toast.makeText(this, "카카오로그인 실패", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "카카오로그인 성공", Toast.LENGTH_SHORT).show()

                    // 사용자 정보 요청
                    UserApiClient.instance.me { user, error ->
                        if (user != null) {
                            val uid: String = user.id.toString()
                            val intent = Intent(this, SignupActivity2::class.java)
                            intent.putExtra("kakao_uid", uid)
                            intent.putExtra("login_type", "kakao")

                            startActivity(intent)
                            finish()

                        }
                    }

                }
            }

            if (AuthApiClient.instance.hasToken()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }

            // 카카오톡이 사용가능하면 이를 이용하여 로그인하고 없으면 카카오계정으로 로그인하기
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}