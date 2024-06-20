package com.example.finalproject

data class ChatGptResponse(
    val choices: List<Choice> //응답 선택지
) {
    data class Choice(
        val message: Message //응답 메세지
    )
}