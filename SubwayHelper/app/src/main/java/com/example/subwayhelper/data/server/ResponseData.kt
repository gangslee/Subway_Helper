package com.example.subwayhelper.data.server

import com.google.gson.annotations.SerializedName


class ResponseData() {
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