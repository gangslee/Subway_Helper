package com.example.subwayhelper.server

import com.example.subwayhelper.data.server.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST



interface ServiceApi {
    @POST("/subway")
    fun getData(@Body data: AskData?): Call<ResponseData?>?
    @POST("/getLine")
    fun getLine(): Call<ResponseLineData?>
    @POST("/getStationOfLine")
    fun getStationOfLine(@Body data: AskStation?): Call<ResponseStationData?>?
    /*
    @POST("/user/join")
    fun joinUser(@Body data: LatestData?): Call<UserResponse?>?
    @POST("/user/drop")
    fun dropUser(@Body data: LatestData?): Call<UserResponse?>?
     */
}