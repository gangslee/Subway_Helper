package com.example.subwayhelper

import android.app.Application
import android.content.Context
import io.realm.Realm

class SubwayHelperApplication() : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: SubwayHelperApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = SubwayHelperApplication.applicationContext()
        Realm.init(this)
    }
}