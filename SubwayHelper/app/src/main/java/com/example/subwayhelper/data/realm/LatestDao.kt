package com.example.subwayhelper.data.realm


import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

// db에 접근하는 오브젝트

class LatestDao(private val realm: Realm) {


    fun getAllRealm(): RealmResults<LatestData> {
        return realm.where(LatestData::class.java)
            .sort("createAt", Sort.DESCENDING)
            .findAll()

    }

    fun findRealm(id: String): LatestData {
        return realm.where(LatestData::class.java)
            .equalTo("id", id)
            .findFirst() as LatestData
    }

    fun updateRealm(line: String, station: String) {
        realm.executeTransaction {

            val checkData: RealmResults<LatestData> =
                realm.where(LatestData::class.java).equalTo("line", line)
                    .equalTo("station", station).findAll()
            // 현재 해당 데이터가 있는지 확인
            if (checkData.isNullOrEmpty()) {
                // 데이터가 없다면 db 추가
                addRealm(line, station)
            } else {
                // 있다면 순서 변경을 위해 기존 데이터 삭제 후 다시 입력
                // 최신에 검색한 내용이 앞으로 나오기 위함
                checkData.deleteAllFromRealm()
                addRealm(line, station)
            }

            //데이터의 갯수가 5개를 넘어가면 가장 오래된 데이터 삭제
            if (getAllRealm().size > 5) {
                resizeRealm()
            }

            println(getAllRealm()[0])
        }
    }


    fun addRealm(line: String, station: String) {
        val tmp = LatestData()
        tmp.line = line
        tmp.station = station
        realm.copyToRealm(tmp)
    }

    fun resizeRealm() {
        getAllRealm()[getAllRealm().size-1]?.deleteFromRealm()
    }
}