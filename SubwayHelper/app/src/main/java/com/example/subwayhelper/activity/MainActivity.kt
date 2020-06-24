package com.example.subwayhelper.activity

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
import com.example.subwayhelper.data.realm.LatestDao
import com.example.subwayhelper.data.MainViewModel
import com.example.subwayhelper.data.recycler.LatestAdapter
import com.example.subwayhelper.data.server.AskStation
import com.example.subwayhelper.data.server.ResponseLineData
import com.example.subwayhelper.data.server.ResponseStationData
import com.example.subwayhelper.server.RetrofitClient
import com.example.subwayhelper.server.ServiceApi
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var latestAdapter: LatestAdapter
    private val realm: Realm = Realm.getDefaultInstance()

    private var service: ServiceApi? = RetrofitClient.getClient()?.create(ServiceApi::class.java)

    // 쿼리 결과를 저장할 변수
    private val stationArray = ArrayList<String>()
    private val lineArray = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setInitView()
        setListener()

    }


    private fun setInitView() {

        setInitRecycler()
        setInitSpinner()

    }

    private fun setInitRecycler() {
        viewModel.let {
            it.latestLiveData.value?.let {

                latestAdapter =
                    LatestAdapter(it)
                latestAdapter.itemClickListener = {
                    val tmp = LatestDao(realm)
                        .findRealm(it)
                    createIntent(tmp.line, tmp.station, 0)
                    LatestDao(realm)
                        .updateRealm(
                            tmp.line,
                            tmp.station
                        )
                }

                latestView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                latestView.adapter = latestAdapter

            }

            it.latestLiveData.observe(this, Observer { latestAdapter.notifyDataSetChanged() })

        }

    }

    private fun getLineSpinnerData() {
        // 현재 노선의 정보를 받아오기 위한 함수
        showProgress(mainProgress, mainBackground_dim, true)

        Handler().postDelayed({

            // 라즈베리와 연결 시작
            // getLine 를 활용하여 데이터 요청
            service?.getLine()?.enqueue(object : Callback<ResponseLineData?> {
                override fun onResponse(
                    call: Call<ResponseLineData?>?,
                    response: Response<ResponseLineData?>
                ) {
                    val responseLine: ResponseLineData? = response.body()
                    // 요청 받은 데이터를 저장

                    // 총 데이터 갯수를 확인
                    val cnt = responseLine?.line?.size?.minus(1)
                    for (i in 0..cnt!!) {
                        //받아온 정보를 갯수에 맞춰 반복문을 돌면서 배열에 저장
                        val tmpLine = responseLine.line?.get(i)?.line.toString()
                        if (tmpLine.length >= 2) {
                            lineArray.add("${tmpLine}선")
                        } else {
                            lineArray.add("${tmpLine}호선")
                        }
                    }

                    showProgress(mainProgress, mainBackground_dim, false)
                }

                // 연결에 실패하였을때 출력결
                override fun onFailure(call: Call<ResponseLineData?>?, t: Throwable) {
                    //Log.e("서버 연결 실패!", t.message)
                    showProgress(mainProgress, mainBackground_dim, false)

                }
            })
        }, 300)
    }

    private fun setInitSpinner() {

        // Line 정보를 요청하는 함수
        getLineSpinnerData()

        // Line 정보를 가져온후 스피너 설정을 위해 딜레이 설정
        Handler().postDelayed({
            // 가져온 정보를 담아둔 lineSpinner에 저장
            SetSpinner(lineSpinner, lineArray, true)
        }, 900)

    }

    fun SetSpinner(getSpinner: Spinner, data: ArrayList<String>, bool: Boolean) {

        val spinner: Spinner = getSpinner


        if (data.isNotEmpty()) {
            spinner.isEnabled = bool
        } else { // 서버와 연결을 실패하거나 호선 정보가 없는 경우 disable처리
            data.add("호선 정보를 불러올 수 없습니다.")
            spinner.isEnabled = false
            stationSpinner.isEnabled = false
            askButton.isEnabled = false
            latestView.visibility = View.INVISIBLE
        }

        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, data)
        spinner.adapter = arrayAdapter


    }

    private fun getStationSpinnerData() {
        // 해당 호선의 역사 정보를 가져오기 위한 함수
        showProgress(mainProgress, mainBackground_dim, true)

        // 전역 변수인 상태에서 add로 계속 더하면 기존 역 정보가 중복되는 문제
        // 기존 역의 정보를 초기화
        stationArray.clear()

        // 글자가 길면 ~~선, 짧으면 ~~호선임을 구분하여 필요한 부분만 슬라이싱
        var lineText = lineSpinner.selectedItem.toString().replace("호선", "")
        lineText = lineText.replace("선", "")

        /* 분당선의 경우 특이 케이스로 따로 조건절로 추가
        if (lineSpinner.selectedItem.toString().length > 3 || lineSpinner.selectedItem.toString() == "분당선") {
        } else {
        }
        */

        // 라즈베리와 연결 시작
        // getStationOfLine을 활용하여 사용자가 선택한 호선을 보내 해당 호선의 역사 정보를 요청
        service?.getStationOfLine(lineText)
            ?.enqueue(object : Callback<ResponseStationData?> {
                override fun onResponse(
                    call: Call<ResponseStationData?>?,
                    response: Response<ResponseStationData?>
                ) {

                    val responseStation: ResponseStationData? = response.body()
                    // 가져온 정보를 저장
                    val cnt = responseStation?.stations?.size?.minus(1)
                    // 가져온 정보의 길이 확인
                    for (i in 0..cnt!!) {
                        // 가져온 배열에서 하나씩 돌면서 배열에 역사 명 추가
                        stationArray.add(responseStation.stations[i].stationName)

                    }

                    // 가져온 역의 데이터로 역 spinner 설정
                    SetSpinner(stationSpinner, stationArray, true)
                    showProgress(mainProgress, mainBackground_dim, false)
                }

                // 연결에 실패하였을때 출력
                override fun onFailure(
                    call: Call<ResponseStationData?>?,
                    t: Throwable
                ) {
                    stationArray.clear()
                    stationArray.add("역사 정보를 불러올 수 없습니다.")
                    SetSpinner(stationSpinner, stationArray, false)
                    //Log.e("서버 연결 실패!", t.message)
                    showProgress(mainProgress, mainBackground_dim, false)

                }
            })

    }

    private fun setListener() {

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
                // 사용자가 호선을 선택하고 나서 역사 정보를 가져오기 위한 함수
                getStationSpinnerData()
            }

        }

        // 조회버튼을 눌렀을때의 작동
        askButton.setOnClickListener {

            // 다른 액티비티로 넘어가기 위한 준비
            createIntent(
                lineSpinner.selectedItem.toString(),
                stationSpinner.selectedItem.toString(), 1
            )

            // 최근 항목 업데이트
            LatestDao(realm)
                .updateRealm(
                    lineSpinner.selectedItem.toString(),
                    stationSpinner.selectedItem.toString()
                )

        }
    }


    private fun createIntent(line: String, station: String, requestCode: Int) {

        showProgress(mainProgress, mainBackground_dim, true)
        Handler().postDelayed({

            showProgress(mainProgress, mainBackground_dim, false)

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

    private fun showProgress(progressBar: ProgressBar, view: View, show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            view.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            progressBar.visibility = View.GONE
            view.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }


}