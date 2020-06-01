package com.example.subwayhelper.data.server

import com.example.subwayhelper.data.server.LineData
import com.google.gson.annotations.SerializedName


class ResponseStationData() {
    @SerializedName("stations")
    val stations:ArrayList<StationData>?=null

}