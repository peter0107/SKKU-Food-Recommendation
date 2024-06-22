package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient

class CampusActivity : AppCompatActivity() {
    companion object{
        const val PLACE="example_place"
    }
    private lateinit var newIntent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campus)

        val btn_suwon=findViewById<Button>(R.id.button_suwon)
        val btn_seoul=findViewById<Button>(R.id.button_seoul)

        //율전 버튼 눌렀을 때
        btn_suwon.setOnClickListener{
            newIntent=Intent(this,foodActivity::class.java)
            newIntent.putExtra(PLACE,"율전캠퍼스")
            startActivity(newIntent)

        }

        //명륜 버튼 눌렀을 때
        btn_seoul.setOnClickListener{
            newIntent=Intent(this,foodActivity::class.java)
            newIntent.putExtra(PLACE,"명륜캠퍼스")
            startActivity(newIntent)

        }
    }
}