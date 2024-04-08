package com.weare2024.tonight.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weare2024.tonight.activites.ChatingActivity
import com.weare2024.tonight.adapter.ChatListAdapter
import com.weare2024.tonight.data.SampleChatList
import com.weare2024.tonight.databinding.ActivityChatingBinding
import com.weare2024.tonight.databinding.FragmentChatBinding

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    var sampleChatList:MutableList<SampleChatList> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)

        sampleChatList.add(0,SampleChatList("옥지얌","빵빵이"))
        sampleChatList.add(1,SampleChatList("ㅂㅈㄷ","ㅁㄴㅇ"))
        sampleChatList.add(2,SampleChatList("123","345"))
        sampleChatList.add(3,SampleChatList("asd","fga"))
        sampleChatList.add(4,SampleChatList("바보얌","빵빵이"))
        sampleChatList.add(5,SampleChatList("123123123","111"))
        sampleChatList.add(6,SampleChatList("ㅁㄴㅇㅁㄴㅇ","빵빵ㄴㄴ이"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = ChatListAdapter(requireContext(),sampleChatList)
        binding.btn.setOnClickListener { clickBtn() }
        binding.btn2.setOnClickListener { clickBtn2() }


    }

    private fun clickBtn(){

        startActivity(Intent(Intent(context,ChatingActivity::class.java)))

    }
    private fun clickBtn2(){
        val intent =Intent(Intent(requireContext(),ChatingActivity::class.java))
        intent.putExtra("asd","asd")
        startActivity(intent)
    }

}