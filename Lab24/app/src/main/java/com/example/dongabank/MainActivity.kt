package com.example.dongabank

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var lvTigia: ListView
    private lateinit var txtdate: TextView
    private var dstygia = ArrayList<Tygia>()
    private lateinit var myadapter: MyArrayAdapter

    // HashMap để ánh xạ tên hình ảnh từ API với resource ID cục bộ
    private lateinit var imageMap: HashMap<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvTigia = findViewById(R.id.lv1)
        txtdate = findViewById(R.id.txtdate)

        getdate()
        dstygia = ArrayList()
        myadapter = MyArrayAdapter(this, R.layout.layout_listview, dstygia)
        lvTigia.adapter = myadapter

        // Khởi tạo HashMap cho hình ảnh (thêm các ánh xạ khác nếu cần)
        initImageMap()

        val task = TyGiaTask()
        task.execute()
    }

    private fun getdate() {
        // Lấy ngày giờ hệ thống
        val currentDate: Date = Calendar.getInstance().time
        // Format theo định dạng dd/MM/yyyy
        val simpleDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        // Hiển thị lên TextView
        txtdate.text = "Hôm Nay: ${simpleDate.format(currentDate)}"
    }

    private fun initImageMap() {
        imageMap = HashMap()
        // Ánh xạ tên hình ảnh từ API với resource ID trong drawable/mipmap
        imageMap["AUD.png"] = R.drawable.aud
        imageMap["CAD.png"] = R.drawable.cad
        imageMap["CHF.png"] = R.drawable.chf
        imageMap["EUR.png"] = R.drawable.eur
        imageMap["GBP.png"] = R.drawable.gbp
        imageMap["JPY.png"] = R.drawable.jpy
        imageMap["USD.png"] = R.drawable.usd
        imageMap["VND.png"] = R.drawable.vnd

    }

    inner class TyGiaTask : AsyncTask<Void, Void, ArrayList<Tygia>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<Tygia> {
            val ds = ArrayList<Tygia>()
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            var json = ""
            try {
                val url = URL("https://dongabank.com.vn/exchange/export")
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8")
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible)")
                connection.setRequestProperty("Accept", "*/*")

                val `is` = connection.inputStream
                val isr = InputStreamReader(`is`, "UTF-8")
                reader = BufferedReader(isr)
                val builder = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                json = builder.toString()

                // Bỏ hai ngoặc tròn trong dữ liệu trả về
                json = json.replace("(", "")
                json = json.replace(")", "")

                Log.d("JSON_DONGA", json)

                val jsonObject = JSONObject(json)
                val jsonArray: JSONArray = jsonObject.getJSONArray("items")

                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    val tiGia = Tygia()

                    if (item.has("type")) {
                        tiGia.type = item.getString("type")
                    }
                    if (item.has("image")) {
                        val imageName = item.getString("image")
                        tiGia.imageurl = imageName
                        // Lấy Bitmap từ resource cục bộ
                        val resourceId = imageMap[imageName]
                        if (resourceId != null) {
                            val bitmap = BitmapFactory.decodeResource(resources, resourceId)
                            tiGia.bitmap = bitmap
                        } else {
                            tiGia.bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                            Log.w("TyGiaTask", "Image resource not found for: $imageName")
                        }
                    } else {
                        tiGia.bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                    }
                    if (item.has("muatienmat")) {
                        tiGia.muatienmat = item.getString("muatienmat")
                    }
                    if (item.has("muack")) {
                        tiGia.muack = item.getString("muack")
                    }
                    if (item.has("bantienmat")) {
                        tiGia.bantienmat = item.getString("bantienmat")
                    }
                    if (item.has("banck")) {
                        tiGia.banck = item.getString("banck")
                    }
                    ds.add(tiGia)
                }
            } catch (ex: Exception) {
                Log.e("TyGiaTask", "Error fetching or parsing data: $ex")
                ex.printStackTrace()
            } finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return ds
        }

        override fun onPreExecute() {
            super.onPreExecute()
            myadapter.clear()
        }

        override fun onPostExecute(result: ArrayList<Tygia>?) {
            super.onPostExecute(result)
            if (result != null && result.isNotEmpty()) {
                myadapter.clear()
                myadapter.addAll(result)
                myadapter.notifyDataSetChanged()
            } else {
                Log.d("TyGiaTask", "No data received or parsed.")
            }
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
        }
    }
}