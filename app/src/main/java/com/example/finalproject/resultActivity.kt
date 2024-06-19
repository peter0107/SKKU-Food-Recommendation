package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class resultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val selectedFood=intent.getStringExtra(foodActivity.FOOD)
        val selectedCampus=intent.getStringExtra(foodActivity.PLACE)

        Toast.makeText(this,selectedFood.toString(),Toast.LENGTH_SHORT).show()

        //GPT api 이용
    }
}