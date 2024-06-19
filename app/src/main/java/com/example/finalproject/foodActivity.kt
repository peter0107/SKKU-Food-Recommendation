package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout

class foodActivity : AppCompatActivity() {

    companion object{
        const val PLACE="example_place"
        const val FOOD="example_food"
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

        //명륜 추천음식
        val foodSeoul= arrayOf("스시","찜닭","짜장면","볶음밥","마라탕")

        //음식추천리스트
        val childCount=linearLayoutFood.childCount

        //명륜일 경우 추천음식 바꾸기(Default=율전추천음식)
        if(selectedCampus=="seoul"){
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
            newIntent=Intent(this,resultActivity::class.java)
            newIntent.putExtra(PLACE,selectedCampus.toString())
            newIntent.putExtra(FOOD,editTextFood.text.toString())
            startActivity(newIntent)
        }


    }
}