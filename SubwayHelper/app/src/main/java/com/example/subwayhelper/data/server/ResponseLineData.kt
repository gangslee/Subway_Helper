package com.example.subwayhelper.data.server

import com.example.subwayhelper.data.server.LineData
import com.google.gson.annotations.SerializedName


class ResponseLineData() {

    // 질의 결과(호선의 정보)를 담을 수 있는 클래스 생성

    @SerializedName("line")
    var line:ArrayList<LineData>?=null

}