package com.example.finalproject

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
interface ChatGptApi{
    @POST("chat/completions")
    fun getChatResponse(
        @Header("Authorization") authHeader: String, //인증 헤더 추가
        @Body request: ChatGptRequest
    ): Call<ChatGptResponse>
}