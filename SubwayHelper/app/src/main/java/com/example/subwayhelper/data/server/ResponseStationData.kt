package com.example.subwayhelper.data.server

import com.google.gson.annotations.SerializedName


class ResponseStationData() {

    // 질의 결과(역사 정보)를 담을 수 있는 클래스 생성

    @SerializedName("stations")
    val stations:ArrayList<StationLineData>?=null

}