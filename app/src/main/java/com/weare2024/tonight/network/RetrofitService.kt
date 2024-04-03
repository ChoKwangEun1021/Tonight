package com.weare2024.tonight.network

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface RetrofitService {
    //1. POST 방식으로 데이터를 보내기
//    @Multipart
//    @POST("/05Retrofit/insertDB.php")
//    fun postDataToServer(@PartMap dataPart: Map<String, String>, @Part filePart: MultipartBody.Part?) : Call<String>
    @Multipart
    @POST("/Tonight/users/insertDB.php")
    fun userInsert(@PartMap userData: Map<String, String>) : Call<String>
}