package com.weare2024.tonight.network

import com.weare2024.tonight.data.BoardDetailData
import com.weare2024.tonight.data.CommentData
import com.weare2024.tonight.data.Images
import com.weare2024.tonight.data.NaverLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface RetrofitService {

    @GET("/v1/nid/me")
    fun getNidUserInfo(@Header("Authorization") authorization: String) : Call<NaverLogin>
    @Multipart
    @POST("/Tonight/board/insertDB.php")
    fun insertBoard(@PartMap dataPart: Map<String, String>, @Part fileParts: List<MultipartBody.Part>?) : Call<String>

    @GET("/Tonight/board/selectDB.php")
    fun selectBoardImgs() : Call<List<Images>>

    @GET("/Tonight/board/selectBoardDetail.php")
    fun boardNoSend(@Query("no") boardNo: Int) : Call<String>

    @GET("/Tonight/board/selectBoardDetail.php")
    fun boardNoSend2(@Query("no") boardNo: Int) : Call<BoardDetailData>

    @GET("/Tonight/comment/selectDB.php")
    fun commentNoSend(@Query("no") boardNo: Int) : Call<List<CommentData>>
}
