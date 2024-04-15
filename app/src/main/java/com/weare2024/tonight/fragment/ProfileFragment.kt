package com.weare2024.tonight.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.activites.ChangeProfileActivity
import com.weare2024.tonight.activites.LoginActivity
import com.weare2024.tonight.databinding.FragmentProfileBinding
import com.weare2024.tonight.firebase.FBAuth

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var drawerLayout: DrawerLayout
    private val spf by lazy { activity?.getSharedPreferences("loginSave", AppCompatActivity.MODE_PRIVATE) }
    private val spf2 by lazy { activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE) }
    private val spfEdt by lazy { spf?.edit() }
    private val spf2Edt by lazy { spf2?.edit() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding

        binding.toolbar.setOnClickListener { clickToolbar() }
        val drawerLogout = binding.nav.setNavigationItemSelectedListener(object :OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                if (p0.itemId == R.id.menu_aa) {
                    Toast.makeText(requireContext(), "로그아웃할거에욤", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(requireContext()).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까")
                        .setPositiveButton("확인") { dialog, id ->
                            FBAuth.auth.signOut()
                            spfEdt?.putBoolean("isLogin", false)
                            spf2Edt?.clear()
                            spfEdt?.apply()
                            spf2Edt?.apply()
                            G.uid = ""
                            G.nickname = ""
                            startActivity(Intent(requireContext(), LoginActivity::class.java))
                            activity?.finish()

                        }.setNegativeButton("취소") { dialog, id ->
                        dialog.dismiss()

                    }.create().show()

                }  else if (p0.itemId == R.id.menu_change) {
                    startActivity(Intent(context, ChangeProfileActivity::class.java))
                    return true
//                    // 닉네임 수정 메뉴가 선택된 경우
//                    Toast.makeText(requireContext(), "닉네임수정이래", Toast.LENGTH_SHORT).show()
//                    val input = EditText(requireContext())
//                    AlertDialog.Builder(requireContext())
//                        .setTitle("새로운 닉네임 입력")
//                        .setMessage("새로운 닉네임을 입력하세요:")
//                        .setView(input)
//                        .setPositiveButton("확인") { dialog, id ->
//                            val newNickname = input.text.toString()
//                            val currentUserUid = FBAuth.getUid()
//
//                            if (currentUserUid != null && currentUserUid.isNotEmpty()) {
//                                val userDocRef = FBRef.userRef.document(currentUserUid)
//                                userDocRef.update("nickname", newNickname)
//                                    .addOnSuccessListener {
//                                        Toast.makeText(
//                                            requireContext(),
//                                            "닉네임을 변경했습니다.",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                    .addOnFailureListener { e ->
//                                        // 실패 이유를 로그로 출력합니다.
//                                        Log.e("Firestore", "닉네임 변경 실패: ${e.message}", e)
//                                        Toast.makeText(
//                                            requireContext(),
//                                            "닉네임 변경에 실패했습니다.",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                            } else {
//                                Log.e("Firestore", "currentUserUid가 null 또는 비어 있습니다.")
//                                Toast.makeText(
//                                    requireContext(),
//                                    "사용자 정보를 찾을 수 없습니다.",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                } else if (p0.itemId == R.id.menu_changeimg) {
//                    // 프로필 이미지 변경 메뉴가 선택된 경우
//                    Toast.makeText(requireContext(), "프사 수정이래", Toast.LENGTH_SHORT).show()
//                    AlertDialog.Builder(requireContext()).setTitle("프로필 이미지 수정").setMessage("프로필 이미지를 수정하시겠습니까?")
//                        .setPositiveButton("확인") { dialog, id ->
//                            // 프로필 이미지 수정 로직을 넣으세요
//                        }.setNegativeButton("취소") { dialog, id ->
//                            dialog.dismiss()
//                        }.create().show()
                }
                return false
            }
            

        })

    }
    private fun clickToolbar(){
        drawerLayout=binding.drawerLayout
        if (drawerLayout!=null) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    // private val spf by lazy { activity?.getSharedPreferences("loginSave", AppCompatActivity.MODE_PRIVATE) }
    //    private val spf2 by lazy { activity?.getSharedPreferences("userInfo", AppCompatActivity.MODE_PRIVATE) }
    //    private val spfEdt by lazy { spf?.edit() }        //spfEdt 이건 로그인 기록 저장
    //    private val spf2Edt by lazy { spf2?.edit() }      //spf2Edt  이건 유저 정보


//    // 파이어베이스에 있는 정보를 찾아서 수정 해서 업뎃 할거임
//    private fun clickImgView(){
//        val alertDialogBuilder = AlertDialog.Builder(requireContext())
//        alertDialogBuilder.setTitle("이미지 수정")
//        alertDialogBuilder.setMessage("이미지를 수정하시겠습니까?")
//        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
//            // 여기에 이미지를 수정하는 코드를 추가합니다.
//            // 이미지를 수정하는 작업을 수행한 후에 토스트 메시지를 표시할 수 있습니다.
//            // Toast.makeText(requireContext(), "이미지를 변경했습니다.", Toast.LENGTH_SHORT).show()
//        }
//        alertDialogBuilder.setNegativeButton("취소") { dialog, which ->
//            // 취소 버튼을 눌렀을 때 수행할 작업을 여기에 추가할 수 있습니다.
//            dialog.dismiss()
//        }
//        val alertDialog = alertDialogBuilder.create()
//        alertDialog.show()
//    }
//
//
//    private fun clickNickName(){
//        val alertDialogBuilder = AlertDialog.Builder(requireContext())
//        alertDialogBuilder.setTitle("닉네임 수정")
//        alertDialogBuilder.setMessage("닉네임을 수정하시겠습니까?")
//        val input = EditText(requireContext())
//        input.inputType = InputType.TYPE_CLASS_TEXT
//        alertDialogBuilder.setView(input)
//
//        alertDialogBuilder.setPositiveButton("확인") { dialog, which ->
//            val newNickname = input.text.toString()
//            val currentUserUid = FBAuth.getUid()
//
//            // Firestore에서 사용자 문서를 참조하고 닉네임을 업데이트합니다.
//            if (currentUserUid.isNotEmpty()) {
//                val userDocRef = FBRef.userRef.document(currentUserUid)
//                userDocRef.update("nickname", newNickname)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(), "닉네임을 변경했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                    .addOnFailureListener { e ->
//                        // 실패 이유를 로그로 출력합니다.
//                        Log.e("Firestore", "닉네임 변경 실패: ${e.message}", e)
//                        Toast.makeText(requireContext(), "닉네임 변경에 실패했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }
//        alertDialogBuilder.setNegativeButton("취소") { dialog, which ->
//            dialog.dismiss()
//        }
//        val alertDialog = alertDialogBuilder.create()
//        alertDialog.show()
//    }
}