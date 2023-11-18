package com.aas.newspract

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject





class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var madapter : NewsAdopter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    recyclerView.layoutManager = LinearLayoutManager(this)


         fetchData()

        madapter = NewsAdopter(this)
        recyclerView.adapter = madapter


    }

    private fun fetchData() {

        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=f0854a024c71431ba3e424141ebf7ba5"
        val jsonObjectRequest = object :JsonObjectRequest(Request.Method.GET, url, null,

            { response ->
                val newsJsonArray = response.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val newsSource: JSONObject = newsJsonObject.getJSONObject("source")
                    val news = News(
                        newsJsonObject.getString("title"),
//                        newsJsonObject.getString(1),
                        newsSource.getString("name"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                       Utils.getTimeAgo(newsJsonObject.getString("publishedAt")).toString()

                    )
                    newsArray.add(news)
                }

                madapter.updateNews(newsArray)
                progressBar.visibility = View.GONE

            },
            Response.ErrorListener {

                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()

            })

        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {

        val builder =  CustomTabsIntent.Builder()
        val  customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}