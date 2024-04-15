package com.weare2024.tonight.activites

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.kakao.sdk.user.model.AccessTokenInfo
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile5Binding
import com.weare2024.tonight.firebase.FBAuth
import com.weare2024.tonight.firebase.FBRef
import java.util.logging.LogManager

class MyProfileActivity5 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile5Binding.inflate(layoutInflater) }
    var nickname: String = ""
    private var imgUri: Uri? = null

    val ccc = grayColor()
    val aaa = bonColor()
    var job = ""
    fun grayColor(): Int {
        val r = 170
        val g = 170
        val b = 170
        return Color.rgb(r, g, b)
    }

    fun bonColor(): Int {

        val rr = 144
        val gg = 215
        val bb = 253
        return Color.rgb(rr, gg, bb)
    }


    private val spf by lazy { getSharedPreferences("loginSave", MODE_PRIVATE) }
    private val spf2 by lazy { getSharedPreferences("userInfo", MODE_PRIVATE) }
    private val spfEdt by lazy { spf.edit() }
    private val spf2Edt by lazy { spf2.edit() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        overridePendingTransition(R.anim.from_right_enter_xml, R.anim.from_left_enter_xml)



        binding.btnStudent.setOnClickListener { student() }
        binding.btnArbeit.setOnClickListener { Arbeit() }
        binding.btnFreelancer.setOnClickListener { Freelancer() }
        binding.btnCompany.setOnClickListener { Company() }
        binding.btnSelfEmployment.setOnClickListener { SelfEmployment() }
        binding.btnProfession.setOnClickListener { Profession() }
        binding.btnDotor.setOnClickListener { Dotor() }
        binding.btnTecher.setOnClickListener { Techer() }
        binding.btnFinance.setOnClickListener { Finance() }
        binding.btnResearch.setOnClickListener { Research() }
        binding.btnPublicOfficial.setOnClickListener { PublicOfficial() }
        binding.btnCeo.setOnClickListener { Ceo() }
        binding.btnSoldier.setOnClickListener { Soldier() }
        binding.btnJobSeeker.setOnClickListener { JobSeeker() }
        binding.btnCategoryOther.setOnClickListener { CategoryOther() }

        binding.btnNext5.setOnClickListener { clickNext() }


    }

    private fun clickNext() {
        if (job == "") {
            Toast.makeText(this, "직업을 선택해 주세요.", Toast.LENGTH_SHORT).show()

        } else {
            val nickname = intent.getStringExtra("nickname")
            val gender = intent.getStringExtra("gender")
            val imguri = intent.getStringExtra("profileImgUri")
            val height = intent.getStringExtra("height")
            val year = intent.getIntExtra("year", 0)
            val month = intent.getIntExtra("month", 1)
            val day = intent.getIntExtra("day", 2)
            val birth = "$year.$month.$day"
            val area = intent.getStringExtra("area")

            if (intent != null && intent.hasExtra("login_type")) {
                when (intent.getStringExtra("login_type")) {
                    "kakao" -> {

                        val uid = intent.getStringExtra("kakao_uid")
                        val kakaoEmail = "$uid@kakao.com"

                        FBRef.userRef.whereEqualTo("email", kakaoEmail).get().addOnSuccessListener {

                            val user = mutableMapOf<String, String>()
                            user["uid"] = uid.toString()
                            user["email"] = kakaoEmail
                            user["nickname"] = nickname.toString()
                            user["gender"] = gender.toString()
                            user["height"] = height.toString()
                            user["birth"] = birth
                            user["area"] = area.toString()
                            user["work"] = job

                            spfEdt.putBoolean("isLogin", true)
                            spf2Edt.putString("uid", uid)
                            spf2Edt.putString("nickname", nickname)
                            spfEdt.apply()
                            spf2Edt.apply()

                            G.uid = uid.toString()
                            G.nickname = nickname.toString()

                            if (nickname != null) {
                                FBRef.userRef.document(nickname).set(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            }
                        }

                        userProfileImgUpload()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    }

                    "naver" -> {
                        val uid = intent.getStringExtra("naver_uid")
                        val naverEmail = intent.getStringExtra("naver_email")

                        FBRef.userRef.whereEqualTo("email", naverEmail).get().addOnSuccessListener {

                            val user = mutableMapOf<String, String>()
                            user["uid"] = uid.toString()
                            user["email"] = naverEmail.toString()
                            user["nickname"] = nickname.toString()
                            user["gender"] = gender.toString()
                            user["height"] = height.toString()
                            user["birth"] = birth
                            user["area"] = area.toString()
                            user["work"] = job

                            spfEdt.putBoolean("isLogin", true)
                            spf2Edt.putString("uid", uid)
                            spf2Edt.putString("nickname", nickname)
                            spfEdt.apply()
                            spf2Edt.apply()

                            G.uid = uid.toString()
                            G.nickname = nickname.toString()

                            if (nickname != null) {
                                FBRef.userRef.document(nickname).set(user).addOnSuccessListener {
                                    Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }

                        userProfileImgUpload()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    }

                    "google" -> {
                        val uid = intent.getStringExtra("google_uid")
                        val googleEmail = intent.getStringExtra("google_email").toString()

                        Toast.makeText(this, "$googleEmail", Toast.LENGTH_SHORT).show()

                        FBRef.userRef.whereEqualTo("email", googleEmail).get()
                            .addOnSuccessListener {

                                val user = mutableMapOf<String, String>()
                                user["uid"] = uid.toString()
                                user["email"] = googleEmail
                                user["nickname"] = nickname.toString()
                                user["gender"] = gender.toString()
                                user["height"] = height.toString()
                                user["birth"] = birth
                                user["area"] = area.toString()
                                user["work"] = job

                                spfEdt.putBoolean("isLogin", true)
                                spf2Edt.putString("uid", uid)
                                spf2Edt.putString("nickname", nickname)
                                spfEdt.apply()
                                spf2Edt.apply()

                                G.uid = uid.toString()
                                G.nickname = nickname.toString()


                                if (nickname != null) {
                                    FBRef.userRef.document(nickname).set(user)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "회원가입이 완료돼었습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                            }
                        userProfileImgUpload()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    }

                    "email" -> {

                        val email = intent.getStringExtra("email").toString()
                        val uid = intent.getStringExtra("email_uid").toString()

                        FBRef.userRef.whereEqualTo("email", email).get()
                            .addOnSuccessListener {

                                val user = mutableMapOf<String, String>()
                                user["uid"] = uid
                                user["email"] = email
                                user["nickname"] = nickname.toString()
                                user["gender"] = gender.toString()
                                user["height"] = height.toString()
                                user["birth"] = birth
                                user["area"] = area.toString()
                                user["work"] = job

                                spfEdt.putBoolean("isLogin", true)
                                spf2Edt.putString("uid", uid)
                                spf2Edt.putString("nickname", nickname)
                                spfEdt.apply()
                                spf2Edt.apply()

                                G.uid = uid
                                G.nickname = nickname.toString()
                                if (nickname != null) {
                                    FBRef.userRef.document(nickname).set(user)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                this,
                                                "회원가입이 완료돼었습니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                }

                            }
                        userProfileImgUpload()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()

                    }
                }

            } else {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun userProfileImgUpload() {

        var name = ""
        val profileImgUri = intent?.getStringExtra("profileImgUri")
        if (profileImgUri != null) {
            imgUri = Uri.parse(profileImgUri)
            if (intent.hasExtra("login_type")) {
                when (intent.getStringExtra("login_type")) {
                    "kakao" -> {
                        name = intent.getStringExtra("kakao_uid").toString()

                        val imgRef: StorageReference =
                            Firebase.storage.getReference("usersImg/$name")

                        imgUri?.apply {
                            imgRef.putFile(this).addOnSuccessListener {

                            }
                        }
                    }
                }
            }
        } else {
            // profileImgUri가 null인 경우 처리
            // 예외를 throw하거나 오류를 기록하거나 다른 적절한 조치를 취할 수 있습니다
        }

    }

    private fun student() {
        binding.btnStudent.setBackgroundColor(ccc)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "학생"
    }

    private fun Arbeit() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(ccc)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "아르바이트"
    }

    private fun Freelancer() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(ccc)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "프리랜서"
    }

    private fun Company() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(ccc)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "회사원"
    }

    private fun SelfEmployment() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(ccc)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "자영업"
    }

    private fun Profession() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(ccc)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "전문직"
    }

    private fun Dotor() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(ccc)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "의료직"
    }

    private fun Techer() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(ccc)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "교육직"
    }

    private fun Finance() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(ccc)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "금융직"
    }

    private fun Research() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(ccc)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "연구,기술직"
    }

    private fun PublicOfficial() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(ccc)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "공무원"
    }

    private fun Ceo() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(ccc)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "사업가"
    }

    private fun Soldier() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(ccc)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "군인"
    }

    private fun JobSeeker() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(ccc)
        binding.btnCategoryOther.setBackgroundColor(aaa)

        job = "취업준비생"
    }

    private fun CategoryOther() {
        binding.btnStudent.setBackgroundColor(aaa)
        binding.btnArbeit.setBackgroundColor(aaa)
        binding.btnFreelancer.setBackgroundColor(aaa)
        binding.btnCompany.setBackgroundColor(aaa)
        binding.btnSelfEmployment.setBackgroundColor(aaa)
        binding.btnProfession.setBackgroundColor(aaa)
        binding.btnDotor.setBackgroundColor(aaa)
        binding.btnTecher.setBackgroundColor(aaa)
        binding.btnFinance.setBackgroundColor(aaa)
        binding.btnResearch.setBackgroundColor(aaa)
        binding.btnPublicOfficial.setBackgroundColor(aaa)
        binding.btnCeo.setBackgroundColor(aaa)
        binding.btnSoldier.setBackgroundColor(aaa)
        binding.btnJobSeeker.setBackgroundColor(aaa)
        binding.btnCategoryOther.setBackgroundColor(ccc)

        job = "기타"
    }

}