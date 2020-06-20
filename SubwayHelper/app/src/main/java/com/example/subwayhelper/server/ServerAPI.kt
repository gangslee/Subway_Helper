package com.example.subwayhelper.server

import com.example.subwayhelper.data.server.*
import retrofit2.Call
import retrofit2.http.*


interface ServiceApi {
    @GET("/subway")
    //fun getData(@Body data: AskData?): Call<ResponseData?>?
    fun getData(@Query("line") line: String, @Query("station") station: String): Call<ResponseData?>?
    @GET("/getLine")
    fun getLine(): Call<ResponseLineData?>
    @GET("/getStationOfLine")
    //@HTTP(method = "GET", path = "/getStationOfLine", hasBody = true)
    //fun getStationOfLine(@Query("line") line: String, @Query("station") station: String): Call<ResponseData?>?
    fun getStationOfLine(@Query("line") line: String): Call<ResponseStationData?>?
    //fun getStationOfLine(@Body data: AskStation?): Call<ResponseStationData?>?
    /*
    @POST("/user/join")
    fun joinUser(@Body data: LatestData?): Call<UserResponse?>?
    @POST("/user/drop")
    fun dropUser(@Body data: LatestData?): Call<UserResponse?>?
     */
}