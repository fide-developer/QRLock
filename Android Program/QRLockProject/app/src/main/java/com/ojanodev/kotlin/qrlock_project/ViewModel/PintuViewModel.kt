package com.ojanodev.kotlin.qrlock_project.ViewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ojanodev.kotlin.qrlock_project.model.Message
import com.ojanodev.kotlin.qrlock_project.model.Pintu

class PintuViewModel : ViewModel(){
    private var tempList : ArrayList<Message> = ArrayList()

    private val query:DatabaseReference = FirebaseDatabase.getInstance().reference.child("Pintu")
    private val pintu : MutableLiveData<ArrayList<Pintu>> by lazy {
        MutableLiveData<ArrayList<Pintu>>().also {
            loadPintu()
        }
    }
    private val pintuStats : MutableLiveData<ArrayList<Message>> = MutableLiveData()


    fun getPintu(): LiveData<ArrayList<Pintu>> {
        return pintu
    }

    fun getPintuStats(code : String): LiveData<ArrayList<Message>> {
        tempList.clear()
        chechPintuStats(code)
        android.os.Handler().postDelayed({
            if(pintuStats.value==null){
                tempList.add(Message("KONEKSI","ERROR"))
                pintuStats.postValue(tempList)
            }
        },5000)
        return pintuStats
    }

    private fun chechPintuStats(code: String) {

        query.child(code).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                tempList.add(Message("ERROR",code))
                pintuStats.postValue(tempList)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.value==false) {
                    Log.d("FUCK", "NOCONN")
                }
                if(!p0.exists()){
                    tempList.add(Message("FALSE",code))
                    pintuStats.postValue(tempList)
                }
                loop@ for (data in p0.children){
                    if(data.key.toString().equals("Kondisi")&&data.value.toString().equals("normal")){
                        chechAuth(code)
                        break@loop
                    }else{
                        tempList.add(Message("PINTU","ERROR"))
                        pintuStats.postValue(tempList)
                    }
                }
            }

        })
    }
    private fun chechAuth(code:String){
        val cek = FirebaseDatabase.getInstance().reference.child("HakAkses")
            .child(FirebaseAuth.getInstance().uid.toString())
            .child(code)

        cek.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                tempList.add(Message("ERROR",code))
                pintuStats.postValue(tempList)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    tempList.add(Message("TRUE",code))
                    pintuStats.postValue(tempList)
                }else{
                    tempList.add(Message("MISS",code))
                    pintuStats.postValue(tempList)
                }
            }

        })
    }

    private fun loadPintu(){

    }
}