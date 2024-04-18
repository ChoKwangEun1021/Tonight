package com.weare2024.tonight.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.DocumentId
import com.weare2024.tonight.R
import com.weare2024.tonight.adapter.HomeAdapter
import com.weare2024.tonight.data.HomeData
import com.weare2024.tonight.databinding.FragmentHomeBinding
import com.weare2024.tonight.firebase.FBRef
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.RewindAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import java.util.Calendar
import java.util.Date

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val manager by lazy { CardStackLayoutManager(requireContext()) }
    private val adapter by lazy { HomeAdapter(requireContext(), itemList) }
    private val itemList = mutableListOf<HomeData>()
    private val nickname = mutableListOf<String>()
    private val birth = mutableListOf<String>()
    private val area = mutableListOf<String>()
    private val work = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cardStackView.layoutManager = manager
        binding.cardStackView.adapter = adapter
        getUsers()
        setupBtn()
    }

    private fun setupBtn() {
        binding.cancel.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.cardStackView.swipe()
        }

        binding.reset.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            binding.cardStackView.rewind()
        }

        binding.like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.cardStackView.swipe()
        }
//        manager.setOverlayInterpolator(LinearInterpolator())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUsers() {

        FBRef.userRef.get().addOnSuccessListener {
            for (data in it){
                nickname.add(data["nickname"].toString())
                val age = data["birth"].toString().substring(0, 4)
                birth.add(age)
                area.add(data["area"].toString())
                work.add(data["work"].toString())
                itemList.add(HomeData(R.drawable.cat2, data["nickname"].toString(), data["birth"].toString(), data["area"].toString()))
            }
            binding.cardStackView.adapter!!.notifyDataSetChanged()
//            val aa = Calendar.getInstance().get(Calendar.YEAR)
//            val bb = (birth[4].toInt() - aa).toString().replace("-", "")
//            AlertDialog.Builder(requireContext()).setMessage("$birth\n$nickname\n$area\n$work\n$bb").create().show()
//            AlertDialog.Builder(requireContext()).setMessage("$aa : ${birth[4].toInt()} = $bb").create().show()
        }

    }

}