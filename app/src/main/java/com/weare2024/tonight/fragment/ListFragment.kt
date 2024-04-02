package com.weare2024.tonight.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.weare2024.tonight.R
import com.weare2024.tonight.adapter.ListAdapter
import com.weare2024.tonight.data.Images
import com.weare2024.tonight.databinding.FragmentListBinding

class ListFragment : Fragment() {

    //0402 2차 커밋&푸쉬

    var items: ArrayList<Images> = ArrayList()

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))
        items.add(Images(R.drawable.sample))

        binding.recyclerView.adapter= ListAdapter(requireContext(), items)

        binding.fab.setOnClickListener {
            Toast.makeText(context, "글 작성 Activity로 연결", Toast.LENGTH_SHORT).show()
        }

    }

}