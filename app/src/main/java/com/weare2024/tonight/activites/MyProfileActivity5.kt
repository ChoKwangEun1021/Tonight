package com.weare2024.tonight.activites

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.ActivityMyProfile5Binding

class MyProfileActivity5 : AppCompatActivity() {

    private val binding by lazy { ActivityMyProfile5Binding.inflate(layoutInflater) }

    val ccc = grayColor()
    val aaa = bonColor()
    var job = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        overridePendingTransition(R.anim.from_right_enter_xml,R.anim.from_left_enter_xml)

        binding.btnNext5.setOnClickListener { clickNext() }

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

    }
    private fun clickNext(){
        if (job ==""){
            Toast.makeText(this, "직업을 선택해 주세요.", Toast.LENGTH_SHORT).show()

        }
        val intent = Intent(this, MainActivity::class.java)
        val uid = intent.getStringExtra("uid")
        val nickname = intent.getStringExtra("nickName")
        val gender = intent.getStringExtra("gender")
        val height = intent.getStringExtra("height")
        val yy = intent.getStringExtra("yy")
        val mm = intent.getStringExtra("mm")
        val dd = intent.getStringExtra("dd")
        val jj =intent.getStringExtra("jj")

        intent.putExtra("yy", yy)
        intent.putExtra("mm", mm)
        intent.putExtra("dd", dd)

        intent.putExtra("jj", jj)
        intent.putExtra("job", job)

        intent.putExtra("kakao_uid", uid)
        intent.putExtra("nickName", nickname)
        intent.putExtra("gender", gender)
        intent.putExtra("height", height)
        AlertDialog.Builder(this).setMessage("인텐트로 넘어온 데이터들 : $nickname  $uid   $gender  $height  $yy:$mm:$dd  $jj  $job").create().show()
        startActivity(intent)
    }

    private fun student(){
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
    private fun Arbeit(){
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
    private fun Freelancer(){
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
    private fun Company(){
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
    private fun SelfEmployment(){
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
    private fun Profession(){
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
    private fun Dotor(){
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
    private fun Techer(){
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
    private fun Finance(){
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
    private fun Research(){
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
    private fun PublicOfficial(){
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
    private fun Ceo(){
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
    private fun Soldier(){
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
    private fun JobSeeker(){
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
    private fun CategoryOther(){
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