package com.example.subwayhelper.data.server

import com.google.gson.annotations.SerializedName


class ResponseData() {

    // 질의 결과(편의시설 정보 조회)를 담을 수 있는 클래스 생성

    @SerializedName("line")
    var line:ArrayList<LineData>?=null

    @SerializedName("toilet")
    var toilet:ArrayList<ToiletData>?=null

    @SerializedName("store")
    var store:ArrayList<StoreData>? =null

    @SerializedName("vanding")
    var vanding:ArrayList<VandingData>?=null

     @SerializedName("address")
     var address:String=""

     @SerializedName("tel")
     var tel:String=""


}