package com.weare2024.tonight.network

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface RetrofitService {
    @Multipart
    @POST("/Tonight/board/insertTest.php")
    fun insertBoard(@PartMap dataPart: Map<String, String>, @Part fileParts: List<MultipartBody.Part>?) : Call<String>

}