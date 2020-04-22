package com.example.subwayhelper.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subwayhelper.R
import com.example.subwayhelper.data.LatestAdapter
import com.example.subwayhelper.data.LatestData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val list: List<LatestData> = listOf(
            LatestData(line = 7, station = "어린이대공원역"),
            LatestData(line = 5, station = "동대문역사문화공원")
        )
        // 임시로 지난 검색 내역을 보여줄 데이터값들

        val adapter = LatestAdapter(list)

        latestView.adapter = adapter
        latestView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // 지난 검색 내역을 보여주는 recyclerview에 배열 연결
        // recyclerviw 스크롤 방향은 수평방


        var lineAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.line_num, android.R.layout.simple_spinner_item
        )
        lineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lineSpinner.setAdapter(lineAdapter)
        // 호선 선택과 관련된 스피너에 아이템 생성

        var stationAdapter = ArrayAdapter.createFromResource(
            applicationContext,
            R.array.line_default, android.R.layout.simple_spinner_item
        )
        stationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stationSpinner.setAdapter(stationAdapter)
        stationSpinner.setEnabled(false)
        // 역 선택과 관련된 스피너에 아이템 생성
        // 호선 선택 전까지 임시로 line_default에 연결

        lineSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // 호선에 따라 역과 관련된 스피너를 생성하기 위해 selectedListener 사용
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 호선 스피너의 현재 값을 가져와서 역과 관련된 스피너 생성
                when (lineSpinner.selectedItem.toString()) {

                    "5호선" -> {
                        stationAdapter = ArrayAdapter.createFromResource(
                            applicationContext,
                            R.array.line5_station, android.R.layout.simple_spinner_item
                        )
                        stationSpinner.setEnabled(true)


                    }

                    "7호선" -> {
                         stationAdapter = ArrayAdapter.createFromResource(
                            applicationContext,
                            R.array.line7_station, android.R.layout.simple_spinner_item
                        )
                        stationSpinner.setEnabled(true)

                    }

                    //지정된 이외 값은 초기값으로 초기화 및 unable상태로 변경
                    else -> {
                        stationAdapter = ArrayAdapter.createFromResource(
                        applicationContext,
                        R.array.line_default, android.R.layout.simple_spinner_item
                    )
                        stationSpinner.setEnabled(false)
                    }


                }

                stationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                stationSpinner.setAdapter(stationAdapter)

            }

        }


    }
}
