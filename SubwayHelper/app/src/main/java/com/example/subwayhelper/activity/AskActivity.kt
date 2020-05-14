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
import com.example.subwayhelper.data.UserResponse
import com.example.subwayhelper.server.RetrofitClient
import com.example.subwayhelper.server.ServiceApi
import kotlinx.android.synthetic.main.activity_ask.*
import kotlinx.android.synthetic.main.content_ask.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AskActivity : AppCompatActivity() {


    private var service: ServiceApi? = RetrofitClient.getClient()?.create(ServiceApi::class.java)
    private var result: UserResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        getDataFromServer() // 라즈베리파이 연결, 데이터를 가져옴
        setInitView() // 초기 화면


    }

    fun setInitView() {

        val intent = getIntent()
        askLineTitle.text = "${intent.getStringExtra("LINE")}"
        askStationTitle.text = "${intent.getStringExtra("STATION")}역에 대한 정보에요."
        // ToolBar영역에 있는 TextView 설정

        when (intent.getStringExtra("LINE")) {
        // 사용자가 선택한 역에 따라 ToolBar영역 색상 변경
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

    fun getDataFromServer() {
        showProgress(askProgress, askBackground_dim, true)
        Handler().postDelayed({
            connectServer()
        }, 500)

    }


    fun connectServer() {
        service?.getData()?.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(
                call: Call<UserResponse?>?,
                response: Response<UserResponse?>
            ) {
                result = response.body()
                Toast.makeText(applicationContext, "서버 연결!", Toast.LENGTH_SHORT).show()
                //askStationText.text = result?.line?.get(0)?.line.toString()

                val cnt = response.body()?.line?.size?.minus(1)

                for (i in 0..cnt!!) {
                    var tmpTextView: TextView = TextView(applicationContext)
                    if (tmpTextView.getParent() != null)
                        (tmpTextView.getParent() as ViewGroup).removeView(
                            tmpTextView
                        )
                    tmpTextView.setText("${result?.line?.get(i)?.line.toString()}호선")
                    tmpTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16F)
                    val lp = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    lp.setMargins(50, 20, 0, 0)
                    tmpTextView.setLayoutParams(lp)

                    LineDynamicLayout.addView(tmpTextView)
                }

                showProgress(askProgress, askBackground_dim, false)
            }

            override fun onFailure(call: Call<UserResponse?>?, t: Throwable) {
                Toast.makeText(applicationContext, "서버 연결 실패!", Toast.LENGTH_SHORT).show()
                Log.e("서버 연결 실패!", t.message)
                askLineTitle.text = "서버 연결 실패!"
                askStationTitle.text = """서버와 통신에 실패했어요. 
                    |인터넷 상태를 확인해주세요.""".trimMargin()
                showProgress(askProgress, askBackground_dim, false)

            }
        })
    }

    fun showProgress(progressBar: ProgressBar, view: View, show: Boolean) {
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
