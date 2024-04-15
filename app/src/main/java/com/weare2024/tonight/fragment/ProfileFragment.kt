package com.weare2024.tonight.fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.weare2024.tonight.G
import com.weare2024.tonight.G.Companion.uid
import com.weare2024.tonight.R
import com.weare2024.tonight.activites.ChangeProfileActivity
import com.weare2024.tonight.activites.LoginActivity
import com.weare2024.tonight.data.UserData
import com.weare2024.tonight.databinding.FragmentProfileBinding
import com.weare2024.tonight.firebase.FBAuth
import com.weare2024.tonight.firebase.FBRef

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var drawerLayout: DrawerLayout
    private val spf by lazy {
        activity?.getSharedPreferences(
            "loginSave",
            AppCompatActivity.MODE_PRIVATE
        )
    }
    private val spf2 by lazy {
        activity?.getSharedPreferences(
            "userInfo",
            AppCompatActivity.MODE_PRIVATE
        )
    }
    private val spfEdt by lazy { spf?.edit() }
    private val spf2Edt by lazy { spf2?.edit() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.toolbar.setOnClickListener { clickToolbar() }
        val drawerLogout = binding.nav.setNavigationItemSelectedListener(object :
            OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                if (p0.itemId == R.id.menu_aa) {
                    Toast.makeText(requireContext(), "로그아웃 해라이 쉨이야.", Toast.LENGTH_SHORT).show()
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

        //닉네임, 프로필이미지 불러오기
        FBRef.userRef.whereEqualTo("uid", uid).get().addOnSuccessListener {
            binding.tv.text = ""
            for (snap in it) {
                val userData: UserData? = snap.toObject(UserData::class.java)
                userData?.apply {

                    binding.tv.append(nickname)

                    val uri = profileImgUri
                    val imgRef: StorageReference = Firebase.storage.getReference("usersImg/" + uri)
                    imgRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(p0: Uri?) {
                            Glide.with(this@ProfileFragment).load(p0).into(binding.iv)
                        }

                    })
                }
            }
        }

    }

    private fun clickToolbar() {
        val tvDrawer = view?.findViewById<TextView>(R.id.tv_drawer)
        val ivDrawer = view?.findViewById<ImageView>(R.id.iv_drawer)

        drawerLayout = binding.drawerLayout

        drawerLayout.openDrawer(GravityCompat.START)

        FBRef.userRef.whereEqualTo("uid", uid).get().addOnSuccessListener {
            tvDrawer?.text = ""
            for (snap in it) {
                val userData: UserData = snap.toObject(UserData::class.java)
                userData.apply {
//                    tvDrawer?.append(nickname)
                    tvDrawer?.text = nickname

                    val uri = profileImgUri
                    val imgRef: StorageReference = Firebase.storage.getReference("usersImg/$uri")
                    imgRef.downloadUrl.addOnSuccessListener(object : OnSuccessListener<Uri> {
                        override fun onSuccess(p0: Uri?) {
                            Glide.with(this@ProfileFragment).load(p0).into(ivDrawer!!)
                        }
                    })
                }
            }
        }

    }
}