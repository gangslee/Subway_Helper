package com.example.subwayhelper.data

import androidx.lifecycle.ViewModel
import io.realm.Realm

class ListViewModel : ViewModel() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }


    private val emoticonDao: LatestDao by lazy{
        LatestDao(realm)
    }

    val latestLiveData: RealmLiveData<LatestData> by lazy{
        RealmLiveData<LatestData>(emoticonDao.getAllRealm())
    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }




}