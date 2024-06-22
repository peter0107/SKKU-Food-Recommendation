package com.example.finalproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class RestaurantAdapter (val context: Context, val restaurantList: ArrayList<Restaurant>) : BaseAdapter() {

    override fun getCount(): Int {
        return restaurantList.size
    }

    override fun getItem(position: Int): Any {
        return restaurantList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(i: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View=LayoutInflater.from(context).inflate(R.layout.recommend_list,null)

        val restTitle=view.findViewById<TextView>(R.id.restaurantTitle)
        val restMenu=view.findViewById<TextView>(R.id.restaurantMenu)
        val restPlace=view.findViewById<TextView>(R.id.restaurantPlace)

        restTitle.setText(restaurantList.get(i).restaurantName)
        restMenu.setText(restaurantList.get(i).restaurantMenu)
        restPlace.setText(restaurantList.get(i).restaurantPlace)

        return view
    }
}