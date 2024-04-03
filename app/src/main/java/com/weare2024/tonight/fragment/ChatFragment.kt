package com.weare2024.tonight.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weare2024.tonight.activites.ChatingActivity
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btn.setOnClickListener { clickBtn() }
        binding.btn2.setOnClickListener { clickBtn() }

    }
    private fun clickBtn(){
        startActivity(Intent(Intent(context,ChatingActivity::class.java)))
    }
}