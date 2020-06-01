package com.example.subwayhelper.data.server

import com.example.subwayhelper.data.server.LineData
import com.google.gson.annotations.SerializedName


class ResponseLineData() {
    @SerializedName("line")
    var line:ArrayList<LineData>?=null

}