package com.example.subwayhelper.data

import com.google.gson.annotations.SerializedName


class UserResponse() {
    @SerializedName("line")
    var line:ArrayList<LineData>?=null

    @SerializedName("exitType")
    var exitType:Int =0

    @SerializedName("transfer")
    var transfer:Int =0

    @SerializedName("toilet")
    private var toilet:ArrayList<ToiletData>?=null

    @SerializedName("store")
    private var store:ArrayList<StoreData>? =null

    @SerializedName("vanding")
    private var vanding:ArrayList<VandingData>?=null

    public fun getCode():ArrayList<LineData>? {
        return line;
    }
/*
    public fun getMessage():String {
        return message;
    }

 */

}