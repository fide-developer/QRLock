package com.ojanodev.kotlin.qrlock_project.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ojanodev.kotlin.qrlock_project.R
import com.ojanodev.kotlin.qrlock_project.TambahHakAkses
import com.ojanodev.kotlin.qrlock_project.model.Users
import kotlinx.android.synthetic.main.list_request_ha.view.*


class RequestHakAksesAdapter (private val context: Context, private val requestlist : ArrayList<Users>) : RecyclerView.Adapter<RequestHakAksesAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return RequestHakAksesAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_request_ha, parent, false))
    }

    override fun getItemCount(): Int {
        return requestlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nama.text = requestlist.get(position).nama
        holder.deskripsi.text = requestlist.get(position).default

        holder.itemView.setOnClickListener({
            var intent = Intent(context,TambahHakAkses::class.java)
            intent.putExtra("UID",requestlist.get(position).uid)
            context.startActivity(intent)
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama = view.listNama
        val deskripsi = view.listDef
    }

}