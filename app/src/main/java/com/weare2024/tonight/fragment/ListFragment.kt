package com.weare2024.tonight.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.weare2024.tonight.R
import com.weare2024.tonight.activites.NewPostActivity
import com.weare2024.tonight.adapter.ListAdapter
import com.weare2024.tonight.data.Images
import com.weare2024.tonight.databinding.FragmentListBinding
import com.weare2024.tonight.network.RetrofitHelper
import com.weare2024.tonight.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {

//    var items: ArrayList<Images> = ArrayList()
    var items = mutableListOf<Images>()

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

        binding.recyclerView.adapter= ListAdapter(requireContext(), items)

        binding.fab.setOnClickListener {
//            Toast.makeText(context, "글 작성 Activity로 연결", Toast.LENGTH_SHORT).show()
            val intent= Intent(requireContext(), NewPostActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onResume() {
        super.onResume()

        selectDB()
    }

    fun selectDB() {
        val retrofit = RetrofitHelper.getRetrofitInstance("http://weare2024.dothome.co.kr")
        val retrofitService = retrofit.create(RetrofitService::class.java)

        retrofitService.selectBoardImgs().enqueue(object : Callback<List<Images>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(p0: Call<List<Images>>, p1: Response<List<Images>>) {
                val data = p1.body()

                items.clear()
                binding.recyclerView.adapter!!.notifyDataSetChanged()

                data?.forEach {
                    items.add(0, it)
                    binding.recyclerView.adapter!!.notifyItemChanged(0)
                }
//                AlertDialog.Builder(requireContext()).setMessage("$data").create().show()
            }

            override fun onFailure(p0: Call<List<Images>> , p1: Throwable) {
                Log.d("abcc", "${p1.message}")
            }

        })

    }

}