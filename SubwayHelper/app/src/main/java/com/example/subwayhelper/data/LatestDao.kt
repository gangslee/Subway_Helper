package com.example.subwayhelper.data



import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

// db에 접근하는 오브젝트

class LatestDao(private val realm: Realm) {


    fun getAll(): RealmResults<LatestData> {
        return realm.where(LatestData::class.java)
            .sort("createAt", Sort.DESCENDING)
            .findAll()

    }

    fun findData(id: String): LatestData {
        return realm.where(LatestData::class.java)
            .equalTo("id", id)
            .findFirst() as LatestData
    }

    fun addOrUpdate(line: String, station: String) {
        realm.executeTransaction {

            val data: RealmResults<LatestData> =
                realm.where(LatestData::class.java).equalTo("line",line).equalTo("station", station).findAll()

            if(data.isNullOrEmpty()) {

                val tmp = LatestData()
                tmp.line = line
                tmp.station = station
                realm.copyToRealm(tmp)
            }

        }
    }


    fun deleteRealm(lastdata: LatestData?) {
        realm.executeTransaction(Realm.Transaction {
            val data: RealmResults<LatestData> =
                realm.where(LatestData::class.java).equalTo("line", lastdata?.line).equalTo("station", lastdata?.station).findAll()
            data.deleteAllFromRealm()
        })

    }
}