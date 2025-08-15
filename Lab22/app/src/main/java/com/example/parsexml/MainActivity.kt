package com.example.parsexml

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var lv1: ListView
    private lateinit var myList: ArrayList<String>
    private lateinit var myAdapter: ArrayAdapter<String>
    private val URL = "https://api.androidhive.info/pizza/?format=xml"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnparse)
        lv1 = findViewById(R.id.lv1)
        myList = ArrayList()
        myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myList)
        lv1.adapter = myAdapter

        btnParse.setOnClickListener {
            LoadExampleTask().execute()
        }
    }

    inner class LoadExampleTask : AsyncTask<Void, Void, ArrayList<String>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<String> {
            val list = ArrayList<String>()
            try {
                val parserFactory = XmlPullParserFactory.newInstance()
                val parser = parserFactory.newPullParser()
                val xml = XMLParser().getXmlFromUrl(URL)
                if (xml == null) return list
                parser.setInput(StringReader(xml))
                var eventType = parser.eventType
                var nodeName: String?
                var dataShow = ""
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            nodeName = parser.name
                            if (nodeName == "id") {
                                parser.next()
                                dataShow = parser.text + "-"
                            } else if (nodeName == "name") {
                                parser.next()
                                dataShow += parser.text
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            nodeName = parser.name
                            if (nodeName == "item") {
                                list.add(dataShow)
                                dataShow = ""
                            }
                        }
                    }
                    eventType = parser.next()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return list
        }

        override fun onPreExecute() {
            super.onPreExecute()
            myAdapter.clear()
        }

        override fun onPostExecute(result: ArrayList<String>) {
            super.onPostExecute(result)
            myAdapter.clear()
            myAdapter.addAll(result)
        }
    }
}