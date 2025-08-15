package com.example.vnexpressnet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyArrayAdapter(
    private val context: Activity,
    private val layoutID: Int,
    private val arr: ArrayList<List>
) : ArrayAdapter<List>(context, layoutID, arr) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(layoutID, null)
        val lst = arr[position]

        val imgitem = rowView.findViewById<ImageView>(R.id.imgView)
        imgitem.setImageBitmap(lst.img)

        val txtTitle = rowView.findViewById<TextView>(R.id.txtTitle)
        txtTitle.text = lst.title

        val txtInfo = rowView.findViewById<TextView>(R.id.txtInfo)
        txtInfo.text = lst.info

        // Set click listener for the whole row
        rowView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(lst.link))
            context.startActivity(intent)
        }

        return rowView
    }
}