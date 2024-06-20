package com.example.finalproject

data class ChatGptRequest(
    val model: String, //사용할 모델 이름
    val messages: List<Message> //메세지 목록
)

data class Message(
    val role: String, //메세지의 역할("user" 또는 "system")
    val content: String //메세지 내용
)