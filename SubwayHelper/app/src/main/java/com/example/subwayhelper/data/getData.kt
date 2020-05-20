package com.example.subwayhelper.data

import com.google.gson.annotations.SerializedName

class getData (inputLine: String, inputStation: String){

    @SerializedName("line")
    val line = inputLine

    @SerializedName("station")
    val station = inputStation

}