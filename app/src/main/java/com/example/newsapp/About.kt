package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar!!.setTitle("About")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val imgUrl = "https://i.ibb.co/w7jCcGV/FotoDiri.jpg"
        val img = findViewById<ImageView>(R.id.foto)

        Picasso.get().load(imgUrl).into(img)
    }
}
