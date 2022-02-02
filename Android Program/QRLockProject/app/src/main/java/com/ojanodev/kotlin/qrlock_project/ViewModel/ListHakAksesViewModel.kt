package com.ojanodev.kotlin.qrlock_project.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ojanodev.kotlin.qrlock_project.model.Users

class ListHakAksesViewModel : ViewModel(){

    private val users: MutableLiveData<ArrayList<Users>> by lazy {
        MutableLiveData<ArrayList<Users>>().also {
            loadData()
        }
    }

    fun getData() : LiveData<ArrayList<Users>> {
        return users
    }

    fun loadData(){
        FirebaseDatabase.getInstance().reference.child("HakAkses").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(data: DataSnapshot) {
                val array : ArrayList<Users> =  ArrayList()
                for(dt in data.children){
                    if(dt.key.toString().equals("YpyT8j49mXTzUWEb46M8A1EzbyT2")) {
                    }else{
                        val uid = dt.key.toString()
                        val nama = dt.child("nama").value.toString()
                        val default = dt.child("default").value.toString()
                        var qr1 : Boolean ?= false
                        var qr2 : Boolean ?= false
                        var qr3 : Boolean ?= false
                        var qr4 : Boolean ?= false
                        var qr5 : Boolean ?= false
                        var qr6 : Boolean ?= false
                        var qr7 : Boolean ?= false
                        var qr8 : Boolean ?= false
                        var qr9 : Boolean ?= false
                        var qr10 : Boolean ?= false

                        if(dt.childrenCount>=2){
                            for (d in dt.children){
                                if(d.key.toString() == "qrpintu1"){
                                    qr1 = true
                                }
                                if(d.key.toString() == "qrpintu2"){
                                    qr2 = true
                                }
                                if(d.key.toString() == "qrpintu3"){
                                    qr3 = true
                                }
                                if(d.key.toString() == "qrpintu4"){
                                    qr4 = true
                                }
                                if(d.key.toString() == "qrpintu5"){
                                    qr5 = true
                                }
                                if(d.key.toString() == "qrpintu6"){
                                    qr6 = true
                                }
                                if(d.key.toString() == "qrpintu7"){
                                    qr7 = true
                                }
                                if(d.key.toString() == "qrpintu8"){
                                    qr8 = true
                                }
                                if(d.key.toString() == "qrpintu9"){
                                    qr9 = true
                                }
                                if(d.key.toString() == "qrpintu10"){
                                    qr10 = true
                                }
                            }
                        }

                        val obj = Users(uid = uid,nama = nama, default = default, qr1 = qr1, qr2 = qr2,
                            qr3 = qr3,qr4 = qr4,qr5 = qr5,qr6 = qr6,qr7 = qr7,qr8 = qr8,qr9 = qr9,qr10 = qr10)
                        array.add(obj)
                    }
                }
                users.postValue(array)
            }

        })
    }
}