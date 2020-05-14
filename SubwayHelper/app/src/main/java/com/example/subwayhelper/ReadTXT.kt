package com.example.subwayhelper

class ReadTXT(var path:String){

    val context = SubwayHelperApplication.applicationContext()

    fun returnURL():String{

        return context.applicationContext.assets.open(path).bufferedReader().use {
            it.readText()
        }.toString()

    }

}