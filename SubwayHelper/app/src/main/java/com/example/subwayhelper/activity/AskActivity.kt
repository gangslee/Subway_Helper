package com.example.subwayhelper.activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
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
import com.example.subwayhelper.data.server.ResponseData
import com.example.subwayhelper.data.server.AskData
import com.example.subwayhelper.server.RetrofitClient
import com.example.subwayhelper.server.ServiceApi
import kotlinx.android.synthetic.main.activity_ask.*
import kotlinx.android.synthetic.main.content_ask.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.abs


class AskActivity : AppCompatActivity() {


    private var service: ServiceApi? = RetrofitClient.getClient()?.create(ServiceApi::class.java)
    // 통신에 필요한 객체 생성

    // 쿼리 결과를 저장할 변수
    private var result: ResponseData? = null
    private lateinit var askData: AskData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        // 사용자가 입력한 호선, 역 정보 저장
        askData = AskData(
            intent.getStringExtra("LINE"),
            intent.getStringExtra("STATION")
        )

        getDataFromServer(askData) // 라즈베리파이 연결, 데이터를 가져옴
        setInitView(askData) // 초기 화면 구성


    }

    private fun setInitView(AskData: AskData) {

        // ToolBar영역에 있는 TextView 설정
        askLineTitle.text = AskData.line
        askStationTitle.text = "${AskData.station}역에 대한 정보에요."

        // ToolBar 색상 지정
        setToolbarColor(AskData.line)
    }


    //툴바, 이미지 색상 변경과 관련된 함수
    private fun setColorFilter(primaryDark: Int, primary:Int){
        window.statusBarColor = ContextCompat.getColor(this, primaryDark)
        lineImageView.setColorFilter(
            ContextCompat.getColor(this, primary),
            PorterDuff.Mode.SRC_IN
        )
        toiletImageView.setColorFilter(
            ContextCompat.getColor(this, primary),
            PorterDuff.Mode.SRC_IN
        )
        storeImageView.setColorFilter(
            ContextCompat.getColor(this, primary),
            PorterDuff.Mode.SRC_IN
        )
        vandingImageView.setColorFilter(
            ContextCompat.getColor(this, primary),
            PorterDuff.Mode.SRC_IN
        )
        ask_main.setBackgroundColor(
            ContextCompat.getColor(
                this,
                primary
            )
        )
        toolbar_layout.setBackgroundColor(
            ContextCompat.getColor(
                this,
                primary
            )
        )
    }

    private fun setToolbarColor(station: String) {
        // 사용자가 선택한 역에 따라 ToolBar영역 색상 변경
        when (station) {

            "1호선" -> {
                setColorFilter(R.color.line1PrimaryDark,R.color.line1Primary)
            }

            "2호선" -> {
                setColorFilter(R.color.line2PrimaryDark,R.color.line2Primary)
            }

            "3호선" -> {
                setColorFilter(R.color.line3PrimaryDark,R.color.line3Primary)
            }

            "4호선" -> {
                setColorFilter(R.color.line4PrimaryDark,R.color.line4Primary)
            }

            "5호선" -> {
                setColorFilter(R.color.line5PrimaryDark,R.color.line5Primary)
            }

            "6호선" -> {
                setColorFilter(R.color.line6PrimaryDark,R.color.line6Primary)
            }

            "7호선" -> {
                setColorFilter(R.color.line7PrimaryDark,R.color.line7Primary)
            }

            "8호선" -> {
                setColorFilter(R.color.line8PrimaryDark,R.color.line8Primary)
            }

            "9호선" -> {
                setColorFilter(R.color.line9PrimaryDark,R.color.line9Primary)
            }

            "경의중앙선" -> {
                setColorFilter(R.color.lineGyeongui_JungangPrimaryDark,R.color.lineGyeongui_JungangPrimary)
            }

            "분당선" -> {
                setColorFilter(R.color.lineBundangPrimaryDark,R.color.lineBundangPrimary)
            }


        }
    }

    private fun getDataFromServer(AskData: AskData) {
        // 서버 연결 및 데이터 요청 시작
        showProgress(askProgress, askBackground_dim, true)
        Handler().postDelayed({
            connectServer(AskData)
        }, 500)

    }


    private fun connectServer(AskData: AskData) {

        // getData 활용
        // 라즈베리와 연결 시작
        // 사용자가 선택한 호선과 역 정보를 보냄 그리고 역사 정보를 요청함
        // getData에서 intent로 받은 사용자가 입력한 호선과 역사를 보냄
        service?.getData(AskData.line, AskData.station)?.enqueue(object : Callback<ResponseData?> {
            override fun onResponse(
                call: Call<ResponseData?>?,
                response: Response<ResponseData?>
            ) {

                result = response.body() // 쿼리 결과 저장

                // 주소 관련 정보 추가 부분
                // 주소 정보가 없다면 해당 정보가 없다고 출력
                // 가져온 정보를 text로 설정
                if(result?.address.isNullOrEmpty()){
                    addressText.text = "주소 정보가 없습니다."
                    callNumText.text = "전화번호 정보가 없습니다."
                }else{ // 있는 경우 textview 설정
                    addressText.text = result?.address
                    callNumText.text = result?.tel
                }


                // 환승 정보 추가하는 부분
                // 가져온 환승 정보를 textview로 추가함
                // 호선 정보를 view에 추가하기 위해 몇개의 호선 정보가 넘어왔는지 cnt에 저장
                // 인덱스 범위로 -1 처리 한 값을 저장하였음
                var cnt = result?.line?.size?.minus(1)

                //호선의 정보가 자기 자신(1개) 밖에 없는 경우 '정보 없음' textView 추가
                if (cnt == null || cnt <= 0) {
                    addTextView(lineDynamicLayout, "정보 없음")
                } else {
                    //1개 이상인 경우 반복문을 돌면서 textView 추가
                    //하지만 자기 자신은 제외
                    for (i in 0..cnt) {
                        if ("${result?.line?.get(i)?.line.toString()}호선" != AskData.line && "${result?.line?.get(
                                i
                            )?.line.toString()}선" != AskData.line
                        ) {
                            // 해당 정보에 대해 textview 추가
                            addTextView(
                                lineDynamicLayout,
                                "${result?.line?.get(i)?.line.toString()}호선"
                            )
                        }
                    }
                }


                // 화장실 관련 정보 추가 부분
                // 가져온 화장실 정보를 textview로 추가함
                cnt = result?.toilet?.size?.minus(1)

                // 화장실 관련 정보가 없는 경우
                if (cnt == null || cnt <= 0) {
                    addTextView(toiletDynamicLayout, "정보 없음")
                } else { // 정보가 있을 때는 반복문을 돌며 정보 추가
                    for (i in 0..cnt) {
                        var tmp: String = ""
                        if (result?.toilet?.get(i)?.floor!! < 0) {
                            tmp = "지하 ${abs(result?.toilet?.get(i)?.floor!!)}층 "
                        } else {
                            tmp = "${result?.toilet?.get(i)?.floor.toString()}층 "
                        }

                        // 기져온 포지션의 값이 1이면 개찰구 밖, 0이면 개찰구 안으로 설정
                        if (result?.toilet?.get(i)?.position == 1) {
                            tmp += "개찰구 밖"
                        } else {
                            tmp += "개찰구 안"
                        }
                        // 해당 정보에 대해 textview 추가
                        addTextView(toiletDynamicLayout, tmp)
                    }
                }


                // 편의점 관련 정보 추가 부분
                // 가져온 편의점 정보를 textview로 추가함
                cnt = result?.store?.size?.minus(1)
                if (cnt == null || cnt < 0) {// 편의점 정보가 없다면 정보 없음 출력
                    addTextView(storeDynamicLayout, "정보 없음")
                } else {

                    for (i in 0..cnt) {
                        // 이외에는 편의점의 갯수를 확인한 후 반복문을 돌며 정보 추가
                        var tmp: String = ""
                        if (result?.store?.get(i)?.floor!! < 0) {
                            tmp =
                                "지하 ${abs(result?.store?.get(i)?.floor!!)}층 ${result?.store?.get(i)?.storeType.toString()}"
                        } else {
                            tmp =
                                "${result?.store?.get(i)?.floor.toString()}층 ${result?.store?.get(i)?.storeType.toString()}"
                        }

                        // 해당 정보에 대해 textview 추가
                        addTextView(storeDynamicLayout, tmp)

                    }
                }


                // 자판기 관련 정보 추가 부분
                // 가져온 자판기 정보를 textview로 추가함
                cnt = result?.vanding?.size?.minus(1)
                //println("자판기 정보: ${cnt}")
                if (cnt == null || cnt <= 0) { // 자판기 정보가 없다면 정보 없음 출력
                    addTextView(vandingDynamicLayout, "정보 없음")
                } else {
                    for (i in 0..cnt) {
                        // 이외에는 자판기의 갯수를 확인한 후 반복문을 돌며 정보 추가
                        var tmp: String = ""
                        if (result?.vanding?.get(i)?.floor!! < 0) {
                            tmp =
                                "지하 ${abs(result?.vanding?.get(i)?.floor!!)}층 ${result?.vanding?.get(
                                    i
                                )?.vandingType.toString()}"
                        } else {
                            tmp =
                                "${result?.vanding?.get(i)?.floor.toString()}층 ${result?.vanding?.get(
                                    i
                                )?.vandingType.toString()}"
                        }
                        // 해당 정보에 대해 textview 추가
                        addTextView(vandingDynamicLayout, tmp)
                    }

                }
                // 정보를 모두 불러왔다면 종료
                showProgress(askProgress, askBackground_dim, false)
            }

            // 연결에 실패하였을때 출력
            override fun onFailure(call: Call<ResponseData?>?, t: Throwable) {
                //Log.e("서버 연결 실패!", t.message)
                // 연결이 실패하였을때 화면 상에 연결 실패 상태를 출력함
                Toast.makeText(applicationContext, "서버 연결 실패!", Toast.LENGTH_SHORT).show()
                askLineTitle.text = "서버 연결 실패!"
                askStationTitle.text = "인터넷 상태를 확인해주세요."
                addressText.text = "주소 정보가 없습니다."
                callNumText.text = "연락처 정보가 없습니다."
                showProgress(askProgress, askBackground_dim, false)

            }
        })


    }

    // view에 textView를 추가하는 함수
    fun addTextView(layout: LinearLayout, text: String) {

        // 임시로 값을 넣은 tmpTextView 선언
        // 가져온 데이터 입력 및 글씨 크기 선언
        val tmpTextView: TextView = TextView(applicationContext).apply {
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
            setMargins(10, 10, 0, 0)
        }

        // 위에서 설정한 정보를 textView에 적용
        tmpTextView.layoutParams = lp
        // 뷰에 추가
        layout.addView(tmpTextView)


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