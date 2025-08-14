import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parsejson.R
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    private lateinit var btnParse: Button
    private lateinit var lv: ListView
    private lateinit var myList: ArrayList<String>
    private lateinit var myAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnParse = findViewById(R.id.btnparse)
        lv = findViewById(R.id.lv)
        myList = ArrayList()
        myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, myList)
        lv.adapter = myAdapter

        btnParse.setOnClickListener {
            parseJson()
        }
    }

    private fun parseJson() {
        myList.clear() // clear list before parsing
        try {
            // Đọc file JSON từ assets
            val inputStream = assets.open("computer.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, StandardCharsets.UTF_8)

            // Parse JSON
            val reader = JSONObject(json)
            myList.add(reader.getString("MaDM"))
            myList.add(reader.getString("TenDM"))

            val mArray = reader.getJSONArray("Sanphams")
            for (i in 0 until mArray.length()) {
                val myObj = mArray.getJSONObject(i)
                myList.add("${myObj.getString("MaSP")} - ${myObj.getString("TenSP")}")
                myList.add("${myObj.getInt("SoLuong")} * ${myObj.getInt("DonGia")} = ${myObj.getInt("ThanhTien")}")
                myList.add(myObj.getString("Hinh"))
            }
            myAdapter.notifyDataSetChanged()
        } catch (e: IOException) {
            Toast.makeText(this, "Lỗi đọc file JSON!", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        } catch (e: Exception) {
            Toast.makeText(this, "Lỗi xử lý dữ liệu JSON!", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}