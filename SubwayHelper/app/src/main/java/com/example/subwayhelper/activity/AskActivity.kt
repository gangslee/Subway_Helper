package com.example.subwayhelper.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.subwayhelper.R
import com.example.subwayhelper.data.LatestDao
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_ask.*
import kotlinx.android.synthetic.main.content_main.*

class AskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)


        setInitView()


    }

    fun setInitView(){

        val intent = getIntent()
        askLineTitle.text = "${intent.getStringExtra("LINE")}"
        askStationTitle.text = "${intent.getStringExtra("STATION")}역에 대한 정보에요."

        val window: Window = getWindow()
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)


        when (intent.getStringExtra("LINE")) {

            "5호선" -> {
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.line5PrimaryDark))

                ask_main.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.line5Primary
                    )
                )
                toolbar_layout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.line5Primary
                    )
                )
            }


            "7호선" -> {
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.line7PrimaryDark))

                ask_main.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.line7Primary
                    )
                )

                toolbar_layout.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.line7Primary
                    )
                )
            }
        }

    }


}
