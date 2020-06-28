package com.example.subwayhelper.server

import com.example.subwayhelper.data.server.*
import retrofit2.Call
import retrofit2.http.*


interface ServiceApi {

    // 요청 및 받아올 데이터를 클래스로 작성해두었음.
    // data/server 폴더에 생성 (ResponseData, ResponseLineData, ResponseStationData 등)

    @GET("/subway")
    fun getData(@Query("line") line: String, @Query("station") station: String): Call<ResponseData?>?
    // 사용자가 입력한 호선과 역사를 보내면 해당 역사의 편의시설 정보를 요청

    @GET("/getLine")
    fun getLine(): Call<ResponseLineData?>
    // 지하철 노선 정보를 요청

    @GET("/getStationOfLine")
    fun getStationOfLine(@Query("line") line: String): Call<ResponseStationData?>?
    // 사용자가 해당 호선의 지하철 역사 정보를 요청
}