package com.ojanodev.kotlin.qrlock_project.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ojanodev.kotlin.qrlock_project.model.Users
import kotlinx.android.synthetic.main.list_manage_ha.view.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.TypedArrayUtils.getString
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.ojanodev.kotlin.qrlock_project.R


class ListHakAksesAdapter (private val context: Context, private val hakAksesList : ArrayList<Users>) : RecyclerView.Adapter<ListHakAksesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ListHakAksesAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_manage_ha, parent, false))
    }

    override fun getItemCount(): Int {
        return hakAksesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nama.text = hakAksesList.get(position).nama
        holder.ket.visibility = View.GONE
        var isNuls = true

        if(hakAksesList.get(position).qr1==true){
            Log.d("ASDASD","pintu1"+hakAksesList.get(position).qr1)
            holder.pintu1.visibility = View.VISIBLE
            holder.btn1.visibility = View.VISIBLE
            holder.btn1.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu1",hakAksesList.get(position).nama,"1")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr2==true){
            Log.d("ASDASD","pintu2"+hakAksesList.get(position).qr2)
            holder.pintu2.visibility = View.VISIBLE
            holder.btn2.visibility = View.VISIBLE
            holder.btn2.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu2",hakAksesList.get(position).nama,"2")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr3==true){
            Log.d("ASDASD","pintu3"+hakAksesList.get(position).qr3)
            holder.pintu3.visibility = View.VISIBLE
            holder.btn3.visibility = View.VISIBLE
            holder.btn3.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu3",hakAksesList.get(position).nama,"3")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr4==true){
            Log.d("ASDASD","pintu4")
            holder.pintu4.visibility = View.VISIBLE
            holder.btn4.visibility = View.VISIBLE
            holder.btn4.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu4",hakAksesList.get(position).nama,"4")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr5==true){
            Log.d("ASDASD","pintu5")
            holder.pintu5.visibility = View.VISIBLE
            holder.btn5.visibility = View.VISIBLE
            holder.btn5.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu5",hakAksesList.get(position).nama,"5")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr6==true){
            Log.d("ASDASD","pintu6")
            holder.pintu6.visibility = View.VISIBLE
            holder.btn6.visibility = View.VISIBLE
            holder.btn6.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu6",hakAksesList.get(position).nama,"6")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr7==true){
            Log.d("ASDASD","pintu7")
            holder.pintu7.visibility = View.VISIBLE
            holder.btn7.visibility = View.VISIBLE
            holder.btn7.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu7",hakAksesList.get(position).nama,"7")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr8==true){
            Log.d("ASDASD","pintu8")
            holder.pintu8.visibility = View.VISIBLE
            holder.btn8.visibility = View.VISIBLE
            holder.btn8.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu8",hakAksesList.get(position).nama,"8")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr9==true){
            Log.d("ASDASD","pintu9")
            holder.pintu9.visibility = View.VISIBLE
            holder.btn9.visibility = View.VISIBLE
            holder.btn9.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu9",hakAksesList.get(position).nama,"9")
            }
            isNuls = false
        }
        if(hakAksesList.get(position).qr10==true){
            Log.d("ASDASD","pintu10")
            holder.pintu10.visibility = View.VISIBLE
            holder.btn10.visibility = View.VISIBLE
            holder.btn10.setOnClickListener {
                deleteHakAkses(it,hakAksesList.get(position).uid,"qrpintu10",hakAksesList.get(position).nama,"10")
            }
            isNuls = false
        }


        if(isNuls){
            holder.ket.visibility = View.VISIBLE
            holder.ket.text = "User ini tidak memiliki hak akses!"
        }
    }

    private fun deleteHakAkses(view: View?, uid: String, s: String, user:String,nopintu:String) {
        AlertDialog.Builder(context)
            .setTitle("Hapus hak akses milik ${user}?")
            .setMessage("Hak akses yang akan dihapus adalah hak akses pintu ${nopintu}")
            .setPositiveButton("Hapus",
                DialogInterface.OnClickListener { dialog, which ->
                    FirebaseDatabase.getInstance().reference.child("HakAkses").child(uid).child(s).removeValue().addOnSuccessListener {

                    }
                })
            .setNegativeButton("Batal",
                DialogInterface.OnClickListener { dialog, which -> })
            .show()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama = view.list_nama
        val ket = view.list_keterangan

        val pintu1 = view.pintu1
        val pintu2 = view.pintu2
        val pintu3 = view.pintu3
        val pintu4 = view.pintu4
        val pintu5 = view.pintu5
        val pintu6 = view.pintu6
        val pintu7 = view.pintu7
        val pintu8 = view.pintu8
        val pintu9 = view.pintu9
        val pintu10 = view.pintu10

        val btn1 = view.button1
        val btn2 = view.button2
        val btn3 = view.button3
        val btn4 = view.button4
        val btn5 = view.button5
        val btn6 = view.button6
        val btn7 = view.button7
        val btn8 = view.button8
        val btn9 = view.button9
        val btn10 = view.button10
    }
}