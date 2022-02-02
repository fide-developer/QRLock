package com.ojanodev.kotlin.qrlock_project.Helper

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreQR {
    val db = FirebaseFirestore.getInstance()
    fun getDataString(stringCallBack: StringCallBack){
        var valpass = "error"

        db.collection("qrLock").document("superUser")
            .get()
            .addOnSuccessListener {

                Log.d("asd", "${it.id} => ${it.data}")
                var dataku = it.data
                var dtku = dataku?.get("password")
                valpass = dtku.toString()

                stringCallBack.onCallback(valpass)
            }
            .addOnFailureListener { exception ->
                Log.w("ASD", "Error getting documents.", exception)

                stringCallBack.onCallback(valpass)
            }
    }
    interface StringCallBack {
        fun onCallback(value: String)
        operator fun invoke(function: () -> Unit) {

        }
    }
}