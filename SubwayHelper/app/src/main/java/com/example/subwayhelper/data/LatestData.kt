package com.example.subwayhelper.data

import java.util.*

class LatestData(

    // 이후에 데이터를 저장해서 지난 검색 내역을 보여줄때 primary key로 쓸 id
    var id:String = UUID.randomUUID().toString(),
    var line:Int = 0, // 지하철 호선
    var station:String = "" // 지하철 역 이름

)