package com.example.finalproject

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ChatGptService {
    private val api: ChatGptApi=ApiClient.getClient().create(ChatGptApi::class.java)
    private val apiKey=BuildConfig.GPT_API_KEY
    fun getChatResponseAsync(apikey: String, request: ChatGptRequest, callback: Callback<ChatGptResponse>){
        val authHeader="Bearer $apiKey"
        val call=api.getChatResponse(authHeader,request)
        call.enqueue(callback)
    }

    fun getChatResponsesync(apikey: String,request: ChatGptRequest): Response<ChatGptResponse>{
        Log.d("info","getchatresponsesync")
        val authHeader="Bearer $apiKey"
        val call=api.getChatResponse(authHeader,request)
        return call.execute()
    }
}