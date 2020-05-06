package com.example.subwayhelper.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.subwayhelper.R
import com.example.subwayhelper.data.LatestDao
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_ask.*
import kotlinx.android.synthetic.main.content_main.*

class AskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        val intent = getIntent()

        askLineText.text = intent.getStringExtra("LINE")
        askStationText.text = intent.getStringExtra("STATION")

    }


}
