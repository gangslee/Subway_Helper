package com.example.subwayhelper.server

import com.example.subwayhelper.ReadTXT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var BASE_URL = ReadTXT("url/url.txt").returnURL()
    private var retrofit: Retrofit? = null;

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}