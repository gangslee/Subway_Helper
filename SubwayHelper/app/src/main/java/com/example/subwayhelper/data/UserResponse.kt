package com.example.subwayhelper.data

import com.google.gson.annotations.SerializedName


class UserResponse() {
    @SerializedName("code")
    private var code:Int = 0

    @SerializedName("message")
    private var message:String =""

    public fun getCode():Int {
        return code;
    }

    public fun getMessage():String {
        return message;
    }

}