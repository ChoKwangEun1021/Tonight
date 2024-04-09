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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task

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
        Log.d("aaa","${R.string.kakao_native_app_key_activity}")

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

            R.id.btn_login_naver -> {}
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

    fun google() {

        val signInOptions: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()

        val intent = GoogleSignIn.getClient(this, signInOptions).signInIntent
        resultLauncher.launch(intent)


    }


    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val intent = it.data

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

            val account = task.result
            val id = account.id.toString()
            val email = account.email ?: ""
            Toast.makeText(this, "$id   ,,$email", Toast.LENGTH_SHORT).show()
            val intent1 = Intent(this, SignupActivity2::class.java)
            intent1.putExtra("google_uid", id)
            intent1.putExtra("login_type", "google")


            startActivity(intent1)
            finish()
        }

    fun clickKakao() {

        // 두개의 로그인 요청 콜백함수
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Toast.makeText(this, "카카오로그인 실패", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "카카오로그인 성공", Toast.LENGTH_SHORT).show()

                // 사용자 정보 요청
                UserApiClient.instance.me { user, error ->
                    if (user != null) {
                        val id: String = user.id.toString()
                        val nickname: String = user.kakaoAccount?.profile?.nickname ?: ""

//                        Toast.makeText(this, "$id\n$nickname", Toast.LENGTH_SHORT).show()
//                        G.userAccount = UserAccount(id, nickname)

                        val intent = Intent(this, SignupActivity2::class.java)
                        intent.putExtra("kakao_uid", id)
                        intent.putExtra("login_type", "kakao")
                        intent.putExtra("nickName",nickname)
//

                        startActivity(intent)

//                        finish()

                    }
                }

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