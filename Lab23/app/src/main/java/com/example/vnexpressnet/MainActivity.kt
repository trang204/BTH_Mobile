package com.example.vnexpressnet

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.FileNotFoundException
import java.io.IOException
import java.io.StringReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var lv1: ListView
    private var mylist = ArrayList<List>()
    private lateinit var myadapter: MyArrayAdapter
    private var nodeName: String? = null
    private var title: String = ""
    private var link: String = ""
    private var description: String = ""
    private var des: String = ""
    private var urlStr: String = ""
    private var mIcon_val: Bitmap? = null
    private val URL_FEED = "https://vnexpress.net/rss/tin-moi-nhat.rss"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lv1 = findViewById(R.id.lv1)
        mylist = ArrayList()
        myadapter = MyArrayAdapter(this, R.layout.layout_listview, mylist)
        lv1.adapter = myadapter
        val task = LoadExampleTask()
        task.execute()
    }

    inner class LoadExampleTask : AsyncTask<Void, Void, ArrayList<List>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<List> {
            mylist = ArrayList()
            try {
                // Tạo đối tượng Parser để chứa dữ liệu từ file XML
                val fc = XmlPullParserFactory.newInstance()
                val parser = fc.newPullParser()
                // Tạo mới và gọi đến phương thức getXmlFromUrl(URL)
                val myparser = XMLParser()
                val xml = myparser.getXmlFromUrl(URL_FEED) // getting XML from URL
                // Copy dữ liệu từ String xml vào đối tượng parser
                parser.setInput(StringReader(xml))
                // Bắt đầu duyệt parser
                var eventType = parser.eventType
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_DOCUMENT -> {}
                        XmlPullParser.END_DOCUMENT -> {}
                        XmlPullParser.START_TAG -> {
                            nodeName = parser.name
                            when (nodeName) {
                                "title" -> title = parser.nextText()
                                "link" -> link = parser.nextText()
                                "description" -> {
                                    description = parser.nextText()
                                    try {
                                        urlStr = description.substring(
                                            description.indexOf("src=") + 5,
                                            description.indexOf("></a") - 2
                                        )
                                    } catch (e1: Exception) {
                                        e1.printStackTrace()
                                    }
                                    des = try {
                                        description.substring(description.indexOf("</br>") + 5)
                                    } catch (e1: Exception) {
                                        ""
                                    }
                                    try {
                                        val newurl = URL(urlStr)
                                        mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
                                    } catch (e1: IOException) {
                                        e1.printStackTrace()
                                    }
                                }
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            nodeName = parser.name
                            if (nodeName == "item") {
                                mylist.add(List(mIcon_val, title, des, link))
                            }
                        }
                    }
                    eventType = parser.next()
                }
            } catch (e: org.xmlpull.v1.XmlPullParserException) {
                e.printStackTrace()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return mylist
        }

        override fun onPreExecute() {
            super.onPreExecute()
            myadapter.clear()
        }

        override fun onPostExecute(result: ArrayList<List>) {
            super.onPostExecute(result)
            myadapter.clear()
            myadapter.addAll(result)
        }
    }
}