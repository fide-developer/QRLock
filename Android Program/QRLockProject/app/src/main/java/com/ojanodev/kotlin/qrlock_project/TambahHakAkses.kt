package com.ojanodev.kotlin.qrlock_project

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ojanodev.kotlin.qrlock_project.Helper.ViewDialog
import kotlinx.android.synthetic.main.activity_tambah_hak_akses.*

class TambahHakAkses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_hak_akses)
        val dialog = ViewDialog(this)

        val uid = intent.getStringExtra("UID")


        dialog.showDialog()
        loadData(uid)
        dialog.hideDialog()

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                Log.d("ASD","KO KEPENCET"+isChecked.toString())
                var action = "Tambah"

                if(!isChecked){
                    switch1.isChecked = true;
                    action = "Hapus"
                }else{
                    switch1.isChecked = false;
                }
                actionDialog(uid,action,textView9.text.toString(),"Pintu 1","qrpintu1")
            }
        }
        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch2.isChecked = true;
                    action = "Hapus"
                }else{
                    switch2.isChecked = false;
                }
                actionDialog(uid,action,textView9.text.toString(),"Pintu 2","qrpintu2")
            }
        }
        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch3.isChecked = true;
                    action = "Hapus"
                }else{
                    switch3.isChecked = false;
                }
                actionDialog(uid,action,textView9.text.toString(),"Pintu 3","qrpintu3")
            }
        }
        switch4.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch4.isChecked = true;
                    action = "Hapus"
                }else{
                    switch4.isChecked = false;
                }
                actionDialog(uid,action,textView9.text.toString(),"Pintu 4","qrpintu4")
            }
        }
        switch5.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch5.isChecked = true;
                    action = "Hapus"
                }else{
                    switch5.isChecked = false;
                }
                actionDialog(uid,action,textView9.text.toString(),"Pintu 5","qrpintu5")
            }
        }
        switch6.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch6.isChecked = true;
                    action = "Hapus"
                }else{
                    switch6.isChecked = false;
                }
                actionDialog(uid,action,textView9.text.toString(),"Pintu 6","qrpintu6")
            }
        }
        switch7.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch7.isChecked = true;
                    action = "Hapus"
                }else{
                    switch7.isChecked = false;
                }
                actionDialog(uid,action,textView9.text.toString(),"Pintu 7","qrpintu7")
            }
        }
        switch8.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch8.isChecked = true;
                    action = "Hapus"
                }else{
                    switch8.isChecked = false;
                }
                Toast.makeText(this,"Pintu tidak dapat digunakan!",Toast.LENGTH_SHORT).show()
                //actionDialog(uid,action,textView9.text.toString(),"Pintu 8","qrpintu8")
            }
        }
        switch9.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch9.isChecked = true;
                    action = "Hapus"
                }else{
                    switch9.isChecked = false;
                }
                Toast.makeText(this,"Pintu tidak dapat digunakan!",Toast.LENGTH_SHORT).show()
                //actionDialog(uid,action,textView9.text.toString(),"Pintu 9","qrpintu9")
            }
        }
        switch10.setOnCheckedChangeListener { buttonView, isChecked ->
            if(buttonView.isPressed){
                var action = "Tambah"

                if(!isChecked){
                    switch10.isChecked = true;
                    action = "Hapus"
                }else{
                    switch10.isChecked = false;
                }
                Toast.makeText(this,"Pintu tidak dapat digunakan!",Toast.LENGTH_SHORT).show()
                //actionDialog(uid,action,textView9.text.toString(),"Pintu 10","qrpintu10")
            }
        }


    }

    private fun actionDialog(uid: String, action: String, user:String, pintu:String,qr:String) {
            AlertDialog.Builder(this)
                .setTitle("${action} hak akses milik ${user}?")
                .setMessage("Hak akses yang akan di${action} adalah hak akses pintu ${pintu}")
                .setPositiveButton(action,
                    DialogInterface.OnClickListener { dialog, which ->
                        if(action.equals("Tambah")){
                            FirebaseDatabase.getInstance().reference.child("HakAkses").child(uid)
                                .child(qr).setValue("true").addOnCompleteListener {
                                    Log.d("ASDASD","DATA INPT SUSES")
                                }
                            FirebaseDatabase.getInstance().reference.child("HakAkses").child(uid)
                                .child("default").setValue("true").addOnCompleteListener {

                                }
                        }else{
                            FirebaseDatabase.getInstance().reference.child("HakAkses")
                                .child(uid).child(qr).removeValue().addOnCompleteListener {
                                    Log.d("ASDASD","DATA REMOVE SUKSES")
                                }
                        }
                    })
                .setNegativeButton("Batal",
                    DialogInterface.OnClickListener { dialog, which -> })
                .show()
    }
    fun initSwitchState(){
        switch1.isChecked = false
        switch1.setText("Tidak")

        switch2.isChecked = false
        switch2.setText("Tidak")

        switch3.isChecked = false
        switch3.setText("Tidak")

        switch4.isChecked = false
        switch4.setText("Tidak")

        switch5.isChecked = false
        switch5.setText("Tidak")

        switch6.isChecked = false
        switch6.setText("Tidak")

        switch7.isChecked = false
        switch7.setText("Tidak")

        switch8.isChecked = false
        switch8.setText("Tidak")

        switch9.isChecked = false
        switch9.setText("Tidak")

        switch10.isChecked = false
        switch10.setText("Tidak")
    }
    fun loadData(uid : String){
        FirebaseDatabase.getInstance().reference.child("HakAkses").child(uid).addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                textView9.text = p0.child("nama").value.toString()
                textView10.text = "Hak akses : "

                initSwitchState()
                Log.d("ASD123",p0.child("default").value.toString())

                if(p0.child("default").value.toString().equals("request")){
                    btn_resReq.text = "Tolak Permintaan"
                    btn_resReq.setOnClickListener(View.OnClickListener {
                        FirebaseDatabase.getInstance().reference.child("HakAkses")
                            .child(uid).child("default").setValue("reject").addOnCompleteListener {
                                val intent = Intent(this@TambahHakAkses,ManageHakAkses::class.java)
                                startActivity(intent)
                            }
                    })
                }else{
                    btn_resReq.visibility = View.GONE
                }
                for(data in p0.children){
                    if(data.key.toString().equals("qrpintu1")){
                        switch1.isChecked = true
                        switch1.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu2")){
                        switch2.isChecked = true
                        switch2.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu3")){
                        switch3.isChecked = true
                        switch3.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu4")){
                        switch4.isChecked = true
                        switch4.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu5")){
                        switch5.isChecked = true
                        switch5.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu6")){
                        switch6.isChecked = true
                        switch6.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu7")){
                        switch7.isChecked = true
                        switch7.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu8")){
                        switch8.isChecked = true
                        switch8.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu9")){
                        switch9.isChecked = true
                        switch9.setText("Ya")
                    }
                    if(data.key.toString().equals("qrpintu10")){
                        switch10.isChecked = true
                        switch10.setText("Ya")
                    }
                }
            }

        })
    }
}
