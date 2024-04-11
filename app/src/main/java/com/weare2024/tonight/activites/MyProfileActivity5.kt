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
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile5Binding
import com.weare2024.tonight.firebase.FBAuth
import java.util.logging.LogManager

class MyProfileActivity5 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile5Binding.inflate(layoutInflater) }
    lateinit var nickname: String
    private var imgUri: Uri? = null

    val ccc = grayColor()
    val aaa = bonColor()
    var job = ""

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
        val userRef = Firebase.firestore.collection("kakao_uid")

        if (job == "") {
            Toast.makeText(this, "직업을 선택해 주세요.", Toast.LENGTH_SHORT).show()

        } else {
            val uid = intent.getStringExtra("kakao_uid")
            val nickname = intent.getStringExtra("nickname")
            val gender = intent.getStringExtra("gender")
            val height = intent.getStringExtra("height")
            val year = intent.getIntExtra("year", 0)
            val month = intent.getIntExtra("month", 1)
            val day = intent.getIntExtra("day", 2)
            val jj = intent.getStringExtra("jj")
            val intent = Intent(this, MainActivity::class.java)

            intent.putExtra("kakao_uid", uid)
            intent.putExtra("nickname", nickname)
            intent.putExtra("gender", gender)
            intent.putExtra("height", height)
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            intent.putExtra("day", day)
            intent.putExtra("jj", jj)
            intent.putExtra("job", job)
            AlertDialog.Builder(this)
                .setMessage("인텐트로 넘어온 데이터들 : $nickname  $uid   $gender  $height  $year:$month:$day  $jj  $job")
                .create().show()


        }



        if (intent != null && intent.hasExtra("login_type")) {
            when (intent.getStringExtra("login_type")) {
                "kakao" -> {

                    val uid = intent.getStringExtra("kakao_uid")
                    val kakaoEmail = "$uid@kakao.com"
                    val nickname = ""

                    userRef.whereEqualTo("email", kakaoEmail).get().addOnSuccessListener {

                        val user = mutableMapOf<String, String>()
                        user["uid"] = user.toString()
                        user["email"] = kakaoEmail
                        user["password"] = "카카오로그인"
                        user["nickname"] = nickname

                        spfEdt.putString("uid", uid)
                        spfEdt.putString("nickname", nickname)
                        spfEdt.apply()

                        G.uid = uid.toString()
                        G.nickname = nickname

                        userRef.document().set(user).addOnSuccessListener {
                            Toast.makeText(this, "회원가입이 완료돼었습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    userProfileImgUpload()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                }

            }

        }
        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()


    }

    private fun userProfileImgUpload(){

        var name = ""
        if (intent != null && intent.hasExtra("login_type")){
            when(intent.getStringExtra("login_type")){
                "kakao" -> {
                    name = intent.getStringExtra("kakao_uid").toString()

                    val imgRef:StorageReference = Firebase.storage.getReference("usersImg/$name")

                    imgUri?.apply {
                        imgRef.putFile(this).addOnSuccessListener {

                        }
                    }
                }
            }
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

}