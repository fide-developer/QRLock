package com.ojanodev.kotlin.qrlock_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import com.ojanodev.kotlin.qrlock_project.Helper.ViewDialog


class RegisterActivity : AppCompatActivity() {
    val regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    var pattern = Pattern.compile(regex)
    var isValid = true
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog:ViewDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dialog = ViewDialog(this)

        val actBar = supportActionBar

        auth = FirebaseAuth.getInstance()

        actBar?.setTitle(getString(R.string.titleRegister))

        btn_register.setOnClickListener {
            dialog.showDialog()
            registNewAccount()
        }
    }
    fun registNewAccount(){
        val emailVal = et_email.text.toString()
        val passwordVal = et_password.text.toString()
        val rePasswordVal = et_passwordConfirm.text.toString()


        nullChecker()

        val isEmailValid = checkEmail(emailVal)

        if(!isEmailValid){
            et_email.setError(getString(R.string.err_emailNotValid))
            isValid = false
            dialog.hideDialog()
        }

        if(!passwordVal.equals(rePasswordVal)){
            et_passwordConfirm.setError(getString(R.string.err_passwordNotMatch))
            isValid = false
            dialog.hideDialog()
        }

        if(isValid){
            auth.createUserWithEmailAndPassword(emailVal, passwordVal)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        addSomeData()
                        val homeIntent = Intent(this,HomeActivity::class.java)
                        startActivity(homeIntent)
                        finish()
                    } else {
                        try {
                            throw task.exception!!

                        } catch (existEmail: FirebaseAuthUserCollisionException) {
                            et_email.setError(getString(R.string.err_emailExist))
                            dialog.hideDialog()
                        }
                        catch (weakPassword: FirebaseAuthWeakPasswordException){
                            et_password.setError(weakPassword.reason)
                            dialog.hideDialog()
                        }catch (errConn : FirebaseNetworkException){
                            val err = errConn.message
                         Log.d("TESTCONN",err)
                            dialog.hideDialog()
                        }
                }
                }
        }
    }

    fun nullChecker(){
        if(et_email.text.toString().equals("")||et_email.text.toString().equals(null)){
            et_email.setError(getString(R.string.err_emailNull))
            isValid = false
        }
        if(et_password.text.toString().equals("")||et_password.text.toString().equals(null)){
            et_password.setError(getString(R.string.err_passwordNull))
            isValid = false
        }
        if(et_passwordConfirm.text.toString().equals("")||et_passwordConfirm.text.toString().equals(null)){
            et_passwordConfirm.setError(getString(R.string.err_rePasswordNull))
            isValid = false
        }
    }
    fun checkEmail(email: String): Boolean {
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun addSomeData(){
        val pathDef = "default"
        val defVal = "false"
        val nama = "nama"
        val namaVal = FirebaseAuth.getInstance().currentUser?.email

        val uid = FirebaseAuth.getInstance().uid.toString()
        val database = FirebaseDatabase.getInstance().reference.child("HakAkses").child(uid)

        database.child(pathDef).setValue(defVal)
        database.child(nama).setValue(namaVal)
    }
}
