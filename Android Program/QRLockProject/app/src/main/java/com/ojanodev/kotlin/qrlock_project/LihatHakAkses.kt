package com.ojanodev.kotlin.qrlock_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ojanodev.kotlin.qrlock_project.adapter.HakAksesAdapter
import com.ojanodev.kotlin.qrlock_project.model.HakAkses
import kotlinx.android.synthetic.main.activity_lihat_hak_akses.*
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.ojanodev.kotlin.qrlock_project.Helper.ViewDialog
import com.ojanodev.kotlin.qrlock_project.ViewModel.HakAksesViewModel


class LihatHakAkses : AppCompatActivity() {
    private val listHakAkses: ArrayList<HakAkses> = ArrayList()
    private lateinit var adapter : HakAksesAdapter
    private lateinit var layoutManagers: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_hak_akses)
        val viewDialog = ViewDialog(this)
        viewDialog.showDialog()

        supportActionBar?.setTitle(getString(R.string.menuHakAkses))

        val model = ViewModelProviders.of(this)[HakAksesViewModel::class.java]
        model.getUsers().observe(this, Observer<ArrayList<HakAkses>>{ hakakses ->
            adapter = HakAksesAdapter(this,hakakses)
            val ha=HakAkses("ERR","ERR")
            val haList:ArrayList<HakAkses> = ArrayList()
            haList.add(ha)
            Log.d("MASUK1","SINI COK"+hakakses.size)
            if(hakakses.size == 1) {
                adapter = HakAksesAdapter(this, haList)
            }
                val message = hakakses.get(0).idPintu.toString()
                Log.d("ASDASD",message)
                if(message.equals("request")){
                    button.isEnabled = false
                    button.text = "Permintaan Hak Akses Sedang Diproses"
                }
                if(message.equals("true")){
                    button.isEnabled = true
                    button.text = "Permintaan Hak Akses Sudah Diterima! Perbarui?"
                }
                if(message.equals("reject")){
                    button.isEnabled = true
                    button.text = "Mohon Maaf, Permintaan Hak Akses Ditolak! Minta Hak Akses Lagi?"
                }
                if(message.equals("false")){
                    button.isEnabled = true
                    button.text = getString(R.string.minta_hak_akses)
                }

            rV_hakAkses.adapter = adapter
            viewDialog.hideDialog()
        })

        layoutManagers = LinearLayoutManager(applicationContext)
        adapter = HakAksesAdapter(this,listHakAkses)
        rV_hakAkses.layoutManager = layoutManagers

        button.setOnClickListener {
            viewDialog.showDialog()

            model.getRequest().observe(this, Observer<String>{ message ->
                viewDialog.hideDialog()
                Snackbar.make(it,message,Snackbar.LENGTH_LONG).show()
            })
        }
    }
}
