package com.ojanodev.kotlin.qrlock_project.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ojanodev.kotlin.qrlock_project.R
import com.ojanodev.kotlin.qrlock_project.model.HakAkses
import kotlinx.android.synthetic.main.list_hakakses.view.*

class HakAksesAdapter (private val context: Context,private val hakAksesList : ArrayList<HakAkses>) : RecyclerView.Adapter<HakAksesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_hakakses,parent,false))
    }

    override fun getItemCount(): Int {
        return hakAksesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(position != 0){
            holder.hakAkses?.text = hakAksesList.get(position).pintu

        }
        if (hakAksesList.get(position).pintu.equals("ERR")){

            holder.hakAkses?.text = "Maaf anda tidak memiliki hak akses!"
            holder.hakAkses?.gravity = Gravity.CENTER_HORIZONTAL
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hakAkses = view.tv_listHakAkses
    }
}