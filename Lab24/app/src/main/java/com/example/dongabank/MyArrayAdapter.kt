package com.example.dongabank

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyArrayAdapter(
    private val context: Activity,
    private val resource: Int,
    private val objects: List<Tygia>
) : ArrayAdapter<Tygia>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val item = inflater.inflate(resource, null)

        val tygia = objects[position]

        val imgHinh = item.findViewById<ImageView>(R.id.imgHinh)
        val txtType = item.findViewById<TextView>(R.id.txtType)
        val txtMuaTM = item.findViewById<TextView>(R.id.txtMuaTM)
        val txtBanTM = item.findViewById<TextView>(R.id.txtBanTM)
        val txtMuaCK = item.findViewById<TextView>(R.id.txtMuaCK)
        val txtBanCK = item.findViewById<TextView>(R.id.txtBanCK)

        imgHinh.setImageBitmap(tygia.bitmap)
        txtType.text = tygia.type
        txtMuaTM.text = tygia.muatienmat
        txtBanTM.text = tygia.bantienmat
        txtMuaCK.text = tygia.muack
        txtBanCK.text = tygia.banck

        return item
    }
}