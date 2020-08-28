package com.salam94.priceswebscraping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.salam94.priceswebscraping.adapter.PriceAdapter
import com.salam94.priceswebscraping.model.Price
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch.setOnClickListener {
            runQuery(editQuery.text.toString())
        }

    }

    fun runQuery(query: String){

        val priceList = ArrayList<Price>()
        val linearLayoutManager = LinearLayoutManager(this)

        Thread(Runnable {

            loadEbay(query, priceList)

            // try to touch View of UI thread
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                val adapter = PriceAdapter(priceList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = linearLayoutManager
            })
        }).start()
    }

    fun loadEbay(query: String, priceList: ArrayList<Price>) {

        val urlEbay = "https://www.ebay.com/sch/i.html?_nkw=$query";
        val doc =
            Jsoup.connect(urlEbay).get()
        val textElements = doc.getElementsByClass("s-item__price")
        val nameElements = doc.getElementsByClass("s-item__title")
        val imageElements = doc.getElementsByClass("s-item__image-img")

        for ((elementNo, element) in textElements.withIndex()) {
            if (elementNo < 50) {
                priceList.add(
                    Price(
                        element.toString(), nameElements[elementNo].toString(),
                        imageElements[elementNo].getElementsByTag("img")[0].absUrl("src").toString()
                    )
                )
            } else {
                break
            }
        }
    }
}