package com.ojanodev.kotlin.qrlock_project.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ojanodev.kotlin.qrlock_project.model.HakAkses
import com.google.firebase.database.DataSnapshot






class HakAksesViewModel : ViewModel(){
    var arrayList : ArrayList<HakAkses> = ArrayList()
    val query = FirebaseDatabase.getInstance().reference.child("HakAkses").child(FirebaseAuth.getInstance().uid.toString())

    private val users: MutableLiveData<ArrayList<HakAkses>> by lazy {
        MutableLiveData<ArrayList<HakAkses>>().also {
            loadUsers()
        }
    }
    private val messageRequest: MutableLiveData<String> = MutableLiveData()

    fun getUsers(): LiveData<ArrayList<HakAkses>> {
        return users
    }

    fun getRequest(): LiveData<String>{
        requestHakAkses()
        return messageRequest
    }

    fun requestHakAkses(){
        query.child("default").setValue("request").addOnCompleteListener {task ->
            if(task.isSuccessful){
                messageRequest.postValue("Sukses")
            }else{
                messageRequest.postValue("Error")
            }
        }
    }
    private fun loadUsers(){
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                users.postValue(null)
                arrayList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    if(postSnapshot.key.toString().equals("nama")){

                    }else{
                        var hakAkses = HakAkses(postSnapshot.key.toString(),postSnapshot.value.toString())
                        Log.d("MESSAGE222","asd"+postSnapshot.value.toString()+postSnapshot.key.toString())
                        arrayList.add(hakAkses)
                    }
                }
                users.postValue(arrayList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("LOADERROR", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
    }