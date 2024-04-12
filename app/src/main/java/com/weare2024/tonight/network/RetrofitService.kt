package com.weare2024.tonight.network

import com.weare2024.tonight.data.Images
import com.weare2024.tonight.data.NaverLogin
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface RetrofitService {
    @Multipart
    @POST("/Tonight/board/insertDB.php")
    fun insertBoard(@PartMap dataPart: Map<String, String>, @Part fileParts: List<MultipartBody.Part>?) : Call<String>

    @GET("/Tonight/board/selectDB.php")
    fun selectBoardImgs() : Call<List<Images>>

    @POST("/Tonight/comment/insertDB.php")
    fun insertComment(@PartMap commentData : Map< String, String > ) : Call<String>

    @GET("/v1/nid/me")
    fun getNidUserInfo( @Header("Authorization") authorization : String ) : Call<NaverLogin>
}