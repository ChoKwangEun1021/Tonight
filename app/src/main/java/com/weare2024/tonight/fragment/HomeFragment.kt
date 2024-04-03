package com.weare2024.tonight.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weare2024.tonight.R
import com.weare2024.tonight.adapter.HomeAdapter
import com.weare2024.tonight.data.HomeData
import com.weare2024.tonight.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val itemList = mutableListOf<HomeData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.recyclerView.adapter = HomeAdapter(requireContext(), itemList)
        getUsers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUsers() {
        itemList.add(HomeData(R.drawable.catd, "헬캣", "27", "서울"))
        itemList.add(HomeData(R.drawable.cat2, "로고캣", "30", "인천"))
        itemList.add(HomeData(R.drawable.profle, "휴먼", "25", "부산"))
        itemList.add(HomeData(R.drawable.catd, "샘플", "21", "대전"))
        itemList.add(HomeData(R.drawable.catd, "몰라", "23", "춘천"))
        binding.recyclerView.adapter!!.notifyDataSetChanged()
    }


}