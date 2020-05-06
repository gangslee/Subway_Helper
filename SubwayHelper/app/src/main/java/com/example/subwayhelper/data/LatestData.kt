package com.example.subwayhelper.data

import java.util.*
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LatestData(


    // 이후에 데이터를 저장해서 지난 검색 내역을 보여줄때 primary key로 쓸 id
    @PrimaryKey
    var id:String = UUID.randomUUID().toString(),
    var createAt:Date = Date(),
    var line:String = "", // 지하철 호선
    var station:String = "" // 지하철 역 이름

):RealmObject()