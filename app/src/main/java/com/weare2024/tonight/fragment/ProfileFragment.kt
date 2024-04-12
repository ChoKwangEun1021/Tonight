package com.weare2024.tonight.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.weare2024.tonight.G
import com.weare2024.tonight.R
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.toolbar.setOnClickListener { clickToolbar() }
        val drawerLogout = binding.nav.setNavigationItemSelectedListener(object :OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                if (p0.itemId == R.id.menu_aa){
                    Toast.makeText(requireContext(), "로그아웃할거에욤", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(requireContext()).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까").setPositiveButton("확인"){dialog , id  ->
                        FBAuth.auth.signOut()
                        spfEdt?.putBoolean("isLogin", false)
                        spf2Edt?.clear()
                        spfEdt?.apply()
                        spf2Edt?.apply()
                        G.uid =""
                        G.nickname =""
                        startActivity(Intent(requireContext(),LoginActivity::class.java))
                        activity?.finish()

                    }.setNegativeButton("취소"){ dialog ,id ->
                        dialog.dismiss()

                    }.create().show()


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

}