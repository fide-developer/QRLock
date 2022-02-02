package com.ojanodev.kotlin.qrlock_project.Helper

import android.widget.Toast
import com.google.firebase.database.*

class FirebaseRTB(path:String){
    private var mDatabase: DatabaseReference? = null
    private var mDatabaseReference: DatabaseReference? = null

    init {
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(path)
    }

    fun retrieveOnce() : String{
        var value:String = ""

        mDatabaseReference?.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                value = dataSnapshot.getValue().toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                value = error.toString()
            }
        })
        return value
    }
    fun create(){

    }
    fun delete(){

    }
}