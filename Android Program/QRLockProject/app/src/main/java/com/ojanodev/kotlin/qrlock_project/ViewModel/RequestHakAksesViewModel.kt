package com.ojanodev.kotlin.qrlock_project.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ojanodev.kotlin.qrlock_project.model.Users

class RequestHakAksesViewModel : ViewModel(){
    private val request : MutableLiveData<ArrayList<Users>> by lazy{
        MutableLiveData<ArrayList<Users>>().also{
            loadData()
        }
    }
    private var arrays : ArrayList<Users> = ArrayList()

    fun getData() : LiveData<ArrayList<Users>> {
        return request
    }
    private fun loadData(){
        FirebaseDatabase.getInstance().reference.child("HakAkses").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    arrays.clear()
                    for(data in p0.children){
                        val uid = data.key.toString()
                        if(uid.equals("YpyT8j49mXTzUWEb46M8A1EzbyT2")){}
                        else{
                            for(dt in data.children){
                                if(dt.key.toString().equals("default")&&dt.value.toString().equals("request")){
                                    var def = "Permintaan hak akses baru"
                                    if(data.childrenCount > 2){
                                        def = "Permintaan pembaruan hak akses"
                                    }
                                    val userData = Users(uid = uid,nama = data.child("nama").value.toString(),default = def )
                                    arrays.add(userData)
                                }
                            }
                            request.postValue(arrays)
                        }
                    }
                }
            }

        })
    }
}