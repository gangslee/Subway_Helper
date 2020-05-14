package com.example.subwayhelper.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subwayhelper.R
import com.example.subwayhelper.data.LatestAdapter
import com.example.subwayhelper.data.LatestDao
import com.example.subwayhelper.data.MainViewModel
import com.google.android.material.internal.ContextUtils.getActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var latestAdapter: LatestAdapter
    private val realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setInitView()
        setListener()

    }


    fun setInitView() {
        setInitRecycler()
        setInitSpinner()
    }

    fun setInitRecycler() {
        viewModel?.let {
            it.latestLiveData.value?.let {

                latestAdapter = LatestAdapter(it)
                latestAdapter.itemClickListener = {
                    val tmp = LatestDao(realm).findRealm(it)
                    createIntent(tmp.line, tmp.station, 0)
                    LatestDao(realm)
                        .updateRealm(
                            tmp.line,
                            tmp.station
                        )
                    latestView.scrollToPosition(0) // 목록 맨 위로 이동
                }

                latestView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                latestView.adapter = latestAdapter

            }

            it.latestLiveData.observe(this, Observer { latestAdapter.notifyDataSetChanged() })

        }
    }

    fun setInitSpinner() {
        setSpinner(lineSpinner, R.array.line_num, true)
        // 호선 선택과 관련된 스피너에 데이터 연결
        // Spinner 상태 활성화
        setSpinner(stationSpinner, R.array.line_default, false)
        // 역 선택과 관련된 스피너에 데이터 연결
        // 호선 선택 전까지 임시로 line_default에 연결
        // Spinner 상태 비활성화

    }

    fun setSpinner(spinner: Spinner, lineData: Int, bool: Boolean) {

        val spinner: Spinner = spinner
        var Adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            getApplication(),
            lineData, android.R.layout.simple_spinner_item
        )

        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(Adapter)
        spinner.setEnabled(bool)

    }

    fun setListener() {

        // 호선에 따라 역과 관련된 스피너를 생성하기 위해 selectedListener 사용
        lineSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                        setSpinner(stationSpinner, R.array.line5_station, true)
                        askButton.setEnabled(true)

                    }

                    "7호선" -> {
                        setSpinner(stationSpinner, R.array.line7_station, true)
                        askButton.setEnabled(true)
                    }

                    //지정된 이외 값은 초기값으로ß 초기화 및 unable상태로 변경
                    else -> {
                        setSpinner(stationSpinner, R.array.line_default, false)
                        askButton.setEnabled(false)
                    }
                }

            }

        }

        // 조회버튼을 눌렀을때의 작동
        askButton.setOnClickListener {

            LatestDao(realm)
                .updateRealm(
                    lineSpinner.selectedItem.toString(),
                    stationSpinner.selectedItem.toString()
                )

            createIntent(
                lineSpinner.selectedItem.toString(),
                stationSpinner.selectedItem.toString(), 1
            )

        }
    }


    fun createIntent(line: String, station: String, requestCode: Int) {

        showProgress(mainProgress,mainBackground_dim,true)
        Handler().postDelayed({

            showProgress(mainProgress,mainBackground_dim,false)

            val intent = Intent(
                applicationContext,
                AskActivity::class.java
            ).run {
                putExtra("LINE", line)
                putExtra("STATION", station)
            }

            startActivityForResult(intent, requestCode)
        }, 500)

    }

    fun showProgress(progressBar: ProgressBar, view:View, show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            view.visibility = View.VISIBLE
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            progressBar.visibility = View.GONE
            view.visibility = View.GONE
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }



}
