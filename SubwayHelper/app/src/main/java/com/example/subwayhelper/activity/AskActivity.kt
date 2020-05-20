package com.example.subwayhelper.activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.subwayhelper.R
import com.example.subwayhelper.data.LatestData
import com.example.subwayhelper.data.UserResponse
import com.example.subwayhelper.data.getData
import com.example.subwayhelper.server.RetrofitClient
import com.example.subwayhelper.server.ServiceApi
import kotlinx.android.synthetic.main.activity_ask.*
import kotlinx.android.synthetic.main.content_ask.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AskActivity : AppCompatActivity() {


    private var service: ServiceApi? = RetrofitClient.getClient()?.create(ServiceApi::class.java)
    // 쿼리 결과를 저장할 변수
    private var result: UserResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        // 사용자가 입력한 호선, 역 정보 저장
        val getData: getData =
            getData(intent.getStringExtra("LINE"), intent.getStringExtra("STATION"))

        getDataFromServer(getData) // 라즈베리파이 연결, 데이터를 가져옴
        setInitView(getData) // 초기 화면 구성


    }

    private fun setInitView(getData: getData) {

        // ToolBar영역에 있는 TextView 설정
        askLineTitle.text = getData.line
        askStationTitle.text = "${getData.station}역에 대한 정보에요."

        // ToolBar 색상 지정
        setToolbarColor(getData.line)
    }

    private fun setToolbarColor(station: String) {

        when (station) {
            // 사용자가 선택한 역에 따라 ToolBar영역 색상 변경
            "5호선" -> {
                window.statusBarColor = ContextCompat.getColor(this, R.color.line5PrimaryDark)

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
                window.statusBarColor = ContextCompat.getColor(this, R.color.line7PrimaryDark)

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

    private fun getDataFromServer(getData: getData) {
        showProgress(askProgress, askBackground_dim, true)
        Handler().postDelayed({
            connectServer(getData)
        }, 500)

    }


    private fun connectServer(getData: getData) {

        // 라즈베리와 연결 시작
        // 사용자가 선택한 호선과 역 정보를 보냄 (POST)
        service?.getData(getData)?.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(
                call: Call<UserResponse?>?,
                response: Response<UserResponse?>
            ) {
                result = response.body() // 쿼리 결과 저장

                //호선 정보를 view에 추가하기 위해 몇개의 호선 정보가 넘어왔는지 cnt에 저장
                // 인덱스 범위 문제로 -1 처리 한 값을 저장하였음
                val cnt = result?.line?.size?.minus(1)

                //호선의 정보가 자기 자신(1개) 밖에 없는 경우 '정보 없음' textView 추가
                if (cnt == 0) {
                    addTextView("정보 없음")
                } else {
                    //1개 이상인 경우 반복문을 돌면서 textView 추가
                    //하지만 자기 자신은 제외
                    for (i in 0..cnt!!) {
                        if ("${result?.line?.get(i)?.line.toString()}호선" != getData.line) {
                            addTextView("${result?.line?.get(i)?.line.toString()}호선")
                        }
                    }
                }
                // 정보를 모두 불러왔다면 종료
                showProgress(askProgress, askBackground_dim, false)
            }
            // 연결에 실패하였을때 출력
            override fun onFailure(call: Call<UserResponse?>?, t: Throwable) {
                Toast.makeText(applicationContext, "서버 연결 실패!", Toast.LENGTH_SHORT).show()
                Log.e("서버 연결 실패!", t.message)
                askLineTitle.text = "서버 연결 실패!"
                askStationTitle.text = "서버와 통신에 실패했어요.\n인터넷 상태를 확인해주세요."
                showProgress(askProgress, askBackground_dim, false)

            }
        })
    }

    // view에 textView를 추가하는 함수
    fun addTextView(text: String) {

        // 임시로 값을 넣은 tmpTextView 선언
        // 가져온 데이터 입력 및 글씨 크기 선언
        val tmpTextView: TextView = TextView(applicationContext).apply{
            setText(text)
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13F)
        }
        // 여러개의 텍스트뷰를 추가할 때 발생하는 뷰 관련 에러를 해결하기 위해 뷰 삭제처리
        if (tmpTextView.parent != null)
            (tmpTextView.parent as ViewGroup).removeView(
                tmpTextView
            )

        // textView와 관련된 정보 수정
        // 가로세로 wrap, 마진, 글씨크기 등
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(50, 15, 0, 0)
        }

        // 위에서 설정한 정보를 textView에 적용
        tmpTextView.layoutParams = lp
        // 뷰에 추가
        LineDynamicLayout.addView(tmpTextView)


    }

    fun showProgress(progressBar: ProgressBar, view: View, show: Boolean) {
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
