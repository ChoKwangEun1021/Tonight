package com.weare2024.tonight.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.weare2024.tonight.G
import com.weare2024.tonight.R
import com.weare2024.tonight.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var drawerLayout: DrawerLayout

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
                    Toast.makeText(requireContext(), "asdasdasdasd", Toast.LENGTH_SHORT).show()
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