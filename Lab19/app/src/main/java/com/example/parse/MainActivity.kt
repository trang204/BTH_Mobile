import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.parse.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

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
            parseXml()
        }
    }

    private fun parseXml() {
        try {
            val myInput: InputStream = assets.open("employee.xml")
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(myInput, null)

            var eventType = parser.eventType
            var nodeName: String? = null
            var dataShow = ""

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_DOCUMENT -> {}
                    XmlPullParser.START_TAG -> {
                        nodeName = parser.name
                        if (nodeName == "employee") {
                            dataShow = parser.getAttributeValue(0) + "-" +
                                    parser.getAttributeValue(1) + "-"
                        } else if (nodeName == "name") {
                            parser.next()
                            dataShow += parser.text + "-"
                        } else if (nodeName == "phone") {
                            parser.next()
                            dataShow += parser.text
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        nodeName = parser.name
                        if (nodeName == "employee") {
                            myList.add(dataShow)
                            dataShow = ""
                        }
                    }
                }
                eventType = parser.next()
            }
            myAdapter.notifyDataSetChanged()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        }
    }
}