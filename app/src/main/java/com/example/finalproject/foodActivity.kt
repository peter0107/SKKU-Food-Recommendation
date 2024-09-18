package com.example.finalproject

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import okio.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class foodActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private lateinit var progressDialog: ProgressDialog
    companion object{
        //const val PLACE="example_place"
        const val TIME="example_time"
        const val WEATHER="example_weather"
        const val RESPOND="example_string"
    }
    private lateinit var newIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        //어느 캠퍼스인지 가져오기
        val selectedCampus=intent.getStringExtra(CampusActivity.PLACE)

        //variable 가져오기
        val linearLayoutFood=findViewById<LinearLayout>(R.id.linearlayoutFood)
        val editTextFood=findViewById<EditText>(R.id.editTextFood)
        val btnResult=findViewById<Button>(R.id.button_result)
        progressDialog=ProgressDialog(this)

        //명륜 추천음식
        val foodSeoul= arrayOf("스시","떡볶이","짜장면","족발","김치찌개")

        //음식추천리스트
        val childCount=linearLayoutFood.childCount

        //명륜일 경우 추천음식 바꾸기(Default=율전추천음식)
        if(selectedCampus=="명륜캠퍼스"){
            for(i in 0 until childCount){
                val button=linearLayoutFood.getChildAt(i) as Button
                button.setText(foodSeoul[i])
            }
        }

        //추천음식을 클릭할 때의 이벤트
        for(i in 0 until childCount){
            val button=linearLayoutFood.getChildAt(i) as Button
            button.setOnClickListener{
                editTextFood.setText(button.text.toString())
                editTextFood.setSelection(editTextFood.text.length)
            }
        }

        //결과보기 버튼 눌렀을 때 이벤트
        btnResult.setOnClickListener{
            val GPTquestion="성균관대 ${selectedCampus} 근처 ${editTextFood.text.toString()} 식당을 추천해줘"

            progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog.setCancelable(false)
            progressDialog.show()
            getResponse(GPTquestion)

        }


    }

    private fun getCurrentTime(): String{
        val currentDateTime=LocalDateTime.now()
        val formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return currentDateTime.format(formatter)
    }

    private fun getResponse(question: String) {
        val url = "https://api.openai.com/v1/chat/completions"
        val apikey = BuildConfig.GPT_API_KEY

        val requestBody = """
            {
            "model":"gpt-3.5-turbo",
            "max_tokens": 500,
            "temperature": 1,
            "messages":[
                {
                    "role":"system",
                    "content": "You are Restaurant recommendation assistant. When recommending restaurants, consider the weather, time of day, and the user's preferred food. You should provide the Restaurant name, address, main menu, latitude, longitude of the 5 restaurants corresponding to the food entered by the user."
                },
                {
                    "role": "user",
                    "content": "성균관대 명륜캠퍼스 근처 스시 식당을 추천해줘"
                },
                {
                    "role": "assistant",
                    "content": "(스시나루, 서울특별시 종로구 대학로 10길 24, 모듬 스시, 37.585162,127.000856), (스시이로, 서울특별시 종로구 성균관로 9길 12, 초밥세트, 37.586013, 126.998217), (오스시, 서울특별시 종로구 대학로 8길 29, 사시미 세트, 37.584908, 127.001245), (미소야, 서울특별시 종로구 대학로 11길 17, 모듬 초밥, 37.583763, 127.001937), (스시하루, 서울특별시 종로구 창경궁로 255, 니기리 스시, 37.582601, 127.002394)"
                },
                {
                    "role": "user",
                    "content": "성균관대 율전캠퍼스 근처 소고기 식당을 추천해줘"
                },
                {
                    "role": "assistant",
                    "content": "(육대장 수원율전점, 경기도 수원시 장안구 서부로 2128, 소고기 국밥, 37.294633, 126.971223), (이화정, 경기도 수원시 장안구 서부로 2135, 한우 불고기, 37.294072, 126.971781), (본가 소고기, 경기도 수원시 장안구 율전로 90, 한우 생갈비, 37.296701, 126.971350), (남산돈가스, 경기도 수원시 장안구 서부로 2117, 소고기 돈가스, 37.294996, 126.971029), (연탄집 소고기, 경기도 수원시 장안구 서부로 2116, 연탄불고기, 37.294713, 126.971572)"
                },
                {
                    "role": "user",
                    "content": "$question"
                }
            ]
                
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apikey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body != null) {
                    Log.v("data", body)
                    val jsonObject = JSONObject(body)
                    val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
                    CoroutineScope(Dispatchers.Main).launch{
                        if(jsonArray.length()>0){
                            val firstChoiceObject = jsonArray.getJSONObject(0)
                            val messageObject: JSONObject = firstChoiceObject.getJSONObject("message")
                            val text = messageObject.getString("content")
                            if(text.startsWith("죄송합니다")){
                                progressDialog.dismiss()
                                Toast.makeText(applicationContext,"죄송합니다. 입력하신 음식에 해당하는 식당이 근처에 존재하지 않습니다.",Toast.LENGTH_SHORT).show()
                            }
                            else{
                                val intent=Intent(applicationContext,resultActivity::class.java)
                                val currentTime=getCurrentTime()
                                intent.putExtra(RESPOND,text)
                                intent.putExtra(TIME,currentTime)
                                startActivity(intent)
                                progressDialog.dismiss()
                            }


                        }
                        else{
                            Toast.makeText(applicationContext,"Error 발생",Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Log.v("data", "empty")
                }


            }
        })
    }
}