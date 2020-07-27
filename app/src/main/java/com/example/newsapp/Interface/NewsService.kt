package com.example.newsapp.Interface

import android.provider.ContactsContract
import com.example.newsapp.Model.News
import com.example.newsapp.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {
    @get:GET("v2/sources?apiKey=523449deb93a4439b0a61fdc7f2df8eb")

    val sources: Call<WebSite>

@GET
fun getNewsFromSource(@Url url:String):Call<News>
}