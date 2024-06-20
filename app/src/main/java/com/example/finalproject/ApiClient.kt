package com.example.finalproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    private var retrofit: Retrofit?=null

    fun getClient(): Retrofit{
        if(retrofit==null){
            retrofit=Retrofit.Builder()
                .baseUrl("https://api.openai.com/v1/") //OpenAI API의 기본 URL
                .addConverterFactory(GsonConverterFactory.create()) //JSON 데이터를 kotlin 객체로 변환
                .build()
        }
        return retrofit!!
    }
}