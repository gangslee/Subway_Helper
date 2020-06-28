package com.example.subwayhelper.data.server

import com.google.gson.annotations.SerializedName

class AskData (inputLine: String, inputStation: String){

    //질의할 때 정보를 담아둘 클래스

    @SerializedName("line")
    val line = inputLine // 호선

    @SerializedName("station")
    val station = inputStation // 역사 명

}