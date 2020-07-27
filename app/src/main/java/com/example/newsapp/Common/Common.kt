package com.example.newsapp.Common

import com.example.newsapp.Interface.NewsService
import com.example.newsapp.Remote.RetrofitClient

object Common{
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "523449deb93a4439b0a61fdc7f2df8eb"

    val newsService:NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source:String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?country=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }
}