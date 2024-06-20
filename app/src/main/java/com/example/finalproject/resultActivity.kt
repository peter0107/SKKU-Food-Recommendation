package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class resultActivity : AppCompatActivity() {

    private lateinit var GptQuery: EditText
    private lateinit var chatGptService: ChatGptService
    private lateinit var openAI: OpenAI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)



        //Get Information
        val selectedFood=intent.getStringExtra(foodActivity.FOOD)
        val selectedCampus=intent.getStringExtra(foodActivity.PLACE)

        val GptBtn=findViewById<Button>(R.id.buttonResult)
        val answer=findViewById<TextView>(R.id.textViewAnswer)
        GptQuery=findViewById(R.id.editTextQuery)


        //GPT api 이용
        var chatGptService=ChatGptService()
        GptBtn.setOnClickListener{
            //sendMessage()

            CoroutineScope(Dispatchers.Main).launch(){
                answer.text=getMessage().await()
            }

        }




    }

    private fun getMessage()= CoroutineScope(Dispatchers.IO).async{
        //openAI
        openAI= OpenAI(BuildConfig.GPT_API_KEY)
        //GPT CHAT
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "You are a helpful assistant!"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = "HI"
                )
            )
        )
        val response=openAI.chatCompletion(chatCompletionRequest)
        response.choices.first().message.toString()
    }

    /*private fun sendMessage() {
        Log.d("info","sendMessage 진행")
        val messageContent = GptQuery.text.toString()
        val apiKey = BuildConfig.GPT_API_KEY
        val messages = listOf(Message("user", messageContent))
        val request = ChatGptRequest("gpt-3.5-turbo", messages)
        CoroutineScope(Dispatchers.Main).launch {
            val response=withContext(Dispatchers.IO){
                chatGptService.getChatResponsesync(apiKey,request)
            }
            handleResponse(response)
        }
    }

    private fun handleResponse(response: Response<ChatGptResponse>){
        if(response.isSuccessful){
            Log.d("info","Response 성공")
            val botMessage=response.body()?.choices?.get(0)?.message?.content
            Toast.makeText(applicationContext,botMessage,Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(applicationContext,"Error: ${response.code()}",Toast.LENGTH_LONG).show()
        }
    }
*/
}