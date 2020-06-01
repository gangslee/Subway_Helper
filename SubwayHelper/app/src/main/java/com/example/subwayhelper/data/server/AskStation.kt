package com.example.subwayhelper.data.server

import com.google.gson.annotations.SerializedName

class AskStation (inputLine: String){

    @SerializedName("line")
    val line = inputLine

}