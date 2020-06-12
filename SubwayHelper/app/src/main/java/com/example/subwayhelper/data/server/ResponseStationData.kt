package com.example.subwayhelper.data.server

import com.google.gson.annotations.SerializedName


class ResponseStationData() {
    @SerializedName("stations")
    val stations:ArrayList<StationLineData>?=null

}