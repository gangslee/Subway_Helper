package com.example.subwayhelper.server

import com.example.subwayhelper.data.LatestData
import com.example.subwayhelper.data.UserResponse
import com.example.subwayhelper.data.getData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST



interface ServiceApi {
    @POST("/subway")
    fun getData(@Body data: getData?): Call<UserResponse?>?
    /*
    @POST("/user/join")
    fun joinUser(@Body data: LatestData?): Call<UserResponse?>?
    @POST("/user/drop")
    fun dropUser(@Body data: LatestData?): Call<UserResponse?>?
     */
}