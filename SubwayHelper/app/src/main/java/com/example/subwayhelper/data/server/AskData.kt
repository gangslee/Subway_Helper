package com.example.subwayhelper.data.server

import com.google.gson.annotations.SerializedName

class AskData (inputLine: String, inputStation: String){

    @SerializedName("line")
    val line = inputLine

    @SerializedName("station")
    val station = inputStation

}