package com.example.newsapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetail : AppCompatActivity(){

    lateinit var alertDialog: AlertDialog

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object :WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {}
        }

        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        if(intent != null)
            if(!intent.getStringExtra("webURL").isEmpty())
                webView.loadUrl(intent.getStringExtra("webURL"))
    }
}