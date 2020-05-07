package com.example.subwayhelper.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subwayhelper.R
import com.example.subwayhelper.data.LatestAdapter
import com.example.subwayhelper.data.LatestDao
import com.example.subwayhelper.data.ListViewModel
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var viewModel: ListViewModel? = null
    private lateinit var latestAdapter: LatestAdapter
    private var realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*

        val w: Unit = getWindow().run{
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

 */





        viewModel = application!!.let {
            ViewModelProvider(
                viewModelStore,
                ViewModelProvider.AndroidViewModelFactory(it)
            )
                .get(ListViewModel::class.java)
        }


        viewModel!!.let {
            it.latestLiveData.value?.let {
                latestAdapter = LatestAdapter(it)

                latestView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                latestView.adapter = latestAdapter

                latestAdapter.itemClickListener = {

                    val tmp = LatestDao(realm).findRealm(it)
                    createIntent(tmp.line, tmp.station, 0)
                    LatestDao(realm)
                        .updateRealm(
                            tmp.line,
                            tmp.station
                        )
                    //latestView.scrollToPosition(0)
                }


            }
            it.latestLiveData.observe(this, Observer { latestAdapter.notifyDataSetChanged() })
        }


        SetSpinner(lineSpinner, R.array.line_num).drawSpinner(true)
        // 호선 선택과 관련된 스피너에 아이템 생성
        // Spinner 상태 활성화

        SetSpinner(stationSpinner, R.array.line_default).drawSpinner(false)
        // 역 선택과 관련된 스피너에 아이템 생성
        // 호선 선택 전까지 임시로 line_default에 연결
        // Spinner 상태 비활성화


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
                        SetSpinner(stationSpinner, R.array.line5_station).drawSpinner(true)
                        askButton.setEnabled(true)

                    }

                    "7호선" -> {
                        SetSpinner(stationSpinner, R.array.line7_station).drawSpinner(true)
                        askButton.setEnabled(true)

                    }

                    //지정된 이외 값은 초기값으로 초기화 및 unable상태로 변경
                    else -> {
                        SetSpinner(stationSpinner, R.array.line_default).drawSpinner(false)
                        askButton.setEnabled(false)
                    }
                }

            }

        }


        askButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

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


        })


    }

    fun showProgress(show: Boolean) {
        if (show) {
            askProgress.visibility = View.VISIBLE
            background_dim.visibility = View.VISIBLE
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            askProgress.visibility = View.GONE
            background_dim.visibility = View.GONE
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

    fun createIntent(line: String, station: String, requestCode: Int) {

        showProgress(true)
        Handler().postDelayed({
            showProgress(false)
            val intent = Intent(
                applicationContext,
                AskActivity::class.java
            )
            intent.putExtra("LINE", line)
            intent.putExtra("STATION", station)
            startActivityForResult(intent, requestCode)
        }, 700)

    }

}
