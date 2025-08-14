package com.example.arsyntask

import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var txtMsg: TextView
    private var mytt: MyAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.button1)
        progressBar = findViewById(R.id.progressBar1)
        txtMsg = findViewById(R.id.textView)

        btnStart.setOnClickListener {
            doStart()
        }
    }

    private fun doStart() {
        mytt = MyAsyncTask(this)
        mytt?.execute()
    }

    // AsyncTask đã bị deprecated nhưng vẫn dùng cho mục đích học tập
    class MyAsyncTask(private val context: MainActivity) : AsyncTask<Void, Int, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()
            Toast.makeText(context, "onPreExecute!", Toast.LENGTH_LONG).show()
        }

        override fun doInBackground(vararg params: Void?): Void? {
            for (i in 0..100) {
                SystemClock.sleep(100) // sleep 100ms
                publishProgress(i)
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val progress = values[0] ?: 0
            context.progressBar.progress = progress
            context.txtMsg.text = "$progress%"
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(context, "Update xong roi do!", Toast.LENGTH_LONG).show()
        }
    }
}