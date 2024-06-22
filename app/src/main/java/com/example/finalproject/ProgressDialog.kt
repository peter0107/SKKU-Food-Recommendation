package com.example.finalproject

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ProgressBar
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.FadingCircle

class ProgressDialog(context: Context): Dialog(context)  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_progress)

        val progressBar:ProgressBar=findViewById(R.id.spin_kit)
        val fadingCircle: Sprite =FadingCircle()
        progressBar.indeterminateDrawable=fadingCircle
    }
}