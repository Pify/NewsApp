package com.example.newsapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.Adapter.ListNewsAdapter
import com.example.newsapp.Common.Common
import com.example.newsapp.Interface.NewsService
import com.example.newsapp.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ListNews : AppCompatActivity() {

    var source = "id"
    var webHotUrl: String? = ""
    lateinit var dialog: AlertDialog
    lateinit var mService: NewsService
    lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        //init view
        mService = Common.newsService

        dialog = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener { loadNews(source, true) }

        diagonalLayout.setOnClickListener {
            val detail = Intent(baseContext, NewsDetail::class.java)
            detail.putExtra("webURL", webHotUrl)
            startActivity(detail)
        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)


        if (source.isNotEmpty()) {
            loadNews(source, false)
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if (isRefreshed) {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        dialog.dismiss()

                        Picasso.get()
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)

                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem?.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }
                })
        } else {
            swipe_to_refresh.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<News> {
                    override fun onFailure(call: Call<News>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipe_to_refresh.isRefreshing = false

                        Picasso.get()
                            .load(response.body()!!.articles!![0].urlToImage)
                            .into(top_image)

                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem?.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val data = item.itemId

        if (data == R.id.about) {
            this.startActivity(Intent(this, About::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
