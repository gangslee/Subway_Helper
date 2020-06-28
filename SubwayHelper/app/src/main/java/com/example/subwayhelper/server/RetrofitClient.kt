package com.example.subwayhelper.server

import com.example.subwayhelper.ReadTXT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var BASE_URL = ReadTXT("url/url.txt").returnURL()
    // 접속할 서버의 url은 따로 asset/url/url.txt 파일에 위치
    private var retrofit: Retrofit? = null;

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    } // retrofit과 관련된 객체 초기화 및 생성을 위한 부분
}