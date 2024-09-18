package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import okhttp3.OkHttpClient


class resultActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    companion object{
        const val LATITUDE="example_latitude"
        const val LONGITUDE="example_longitude"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //Get Information
        val GPTResponse = intent.getStringExtra(foodActivity.RESPOND)
        val currentTime=intent.getStringExtra(foodActivity.TIME)
        val listviewRestaurant = findViewById<ListView>(R.id.listviewRecommend)
        val HomeButton=findViewById<Button>(R.id.button_home)
        val TryButton=findViewById<Button>(R.id.button_try)
        val textTime=findViewById<TextView>(R.id.textViewTime)

        textTime.setText(currentTime)
        val restaurantArray = parseRestaurantResponse(GPTResponse?:"")
        val restaurantAdapter=RestaurantAdapter(this,restaurantArray)
        listviewRestaurant.adapter=restaurantAdapter

        listviewRestaurant.onItemClickListener=AdapterView.OnItemClickListener{parent,view,position,id->
            val selectedItem=parent.getItemAtPosition(position) as Restaurant
            val intentOnclick=Intent(this,MapActivity::class.java)
            intentOnclick.putExtra(LATITUDE,selectedItem.latitude)
            intentOnclick.putExtra(LONGITUDE,selectedItem.longitude)
            Log.d("info",selectedItem.restaurantPlace)
            startActivity(intentOnclick)
        }

        //Home화면 돌아가기
        HomeButton.setOnClickListener{
            val intentHome= Intent(this,MainActivity::class.java)
            startActivity(intentHome)
            finish()
        }

        //음식추천 다시받기
        TryButton.setOnClickListener{
            val intentTry=Intent(this,CampusActivity::class.java)
            startActivity(intentTry)
            finish()
        }





    }

        //Data class에 맞게 변환
        private fun parseRestaurantResponse(response: String): ArrayList<Restaurant> {
            val restaurantStrings = response.removePrefix("(").removeSuffix(")").split("), (")

            val restaurantArray = ArrayList<Restaurant>()
            for (restaurantStr in restaurantStrings) {
                val parts=restaurantStr.split(", ")
                if(parts.size==5){
                    val name=parts[0]
                    val place=parts[1]
                    val menu=parts[2]
                    val latitude=parts[3]
                    val longitude=parts[4]
                    val restaurant=Restaurant(name,place,menu,latitude.toDouble(),longitude.toDouble())
                    restaurantArray.add(restaurant)
                }
                else{
                    Log.e("parseRestaurantResponse", "Invalid restaurant format: $restaurantStr")
                }
            }
            return restaurantArray
        }
    }




