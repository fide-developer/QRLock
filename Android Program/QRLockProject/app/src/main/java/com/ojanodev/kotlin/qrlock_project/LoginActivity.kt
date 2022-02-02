package com.ojanodev.kotlin.qrlock_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.android.synthetic.main.activity_login.*
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.ojanodev.kotlin.qrlock_project.Helper.FirebaseRTB
import com.ojanodev.kotlin.qrlock_project.Helper.FirestoreQR
import com.ojanodev.kotlin.qrlock_project.Helper.ViewDialog
import com.ojanodev.kotlin.qrlock_project.Preference.LoginPreference
import kotlinx.android.synthetic.main.activity_register.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var viewDialog: ViewDialog
    lateinit var database: FirebaseRTB
    lateinit var loginPreference:LoginPreference
    lateinit var timer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPreference = LoginPreference(this)
        supportActionBar?.hide()

        viewDialog = ViewDialog(this)

        btn_openRegister.setOnClickListener {
            val regIntent = Intent(this, RegisterActivity::class.java)
            startActivity(regIntent)
        }

        btn_login.setOnClickListener {
            btn_login.isEnabled = false
            viewDialog.showDialog()
            val emailLogin = et_loginEmail.text.toString()
            val emailUP = emailLogin.toUpperCase()
            val passwordLogin = et_loginPassword.text.toString()
            if(emailUP.equals("ADMINISTRATOR")){
                adminLogin(emailLogin,passwordLogin)
            }else{
                loginPreference.saveBool("isAdmin",false)
                authLogin(emailLogin,passwordLogin)
            }
        }
    }
    fun adminLogin(email:String,password:String){
        if(password.equals("")||password.equals(null)){
            et_loginPassword.setError(getString(R.string.err_passwordNull))
            viewDialog.hideDialog()
        }else{
            val path = "superUser"
            database = FirebaseRTB(path)

            val getDtbs : FirestoreQR = FirestoreQR()
            getDtbs.getDataString(object : FirestoreQR.StringCallBack {
                override fun onCallback(value: String) {
                    Log.d("CUKIMAY :",value+"-"+password)
                    if(password.equals(value)){
                        loginPreference.saveBool("isAdmin",true)
                        loginPreference.saveBool("loginStats",true)
                        authLogin("qrlock.project@gmail.com",password)
                    }
                }
            } )
        }
    }
    fun authLogin(emailLogin:String,passwordLogin:String){

        auth = FirebaseAuth.getInstance()

        var isNull = checkNull(emailLogin)

        if(!isNull){
            isNull = checkNull(passwordLogin)
        }else{
            btn_login.isEnabled = true
            et_loginEmail.setError(getString(R.string.err_emailNull))
        }

        if(!isNull){
            auth.signInWithEmailAndPassword(emailLogin,passwordLogin)
                .addOnCompleteListener(this){task ->
                    if(task.isSuccessful){
                        loginPreference.saveBool("loginStats",true)
                        val homeIntent = Intent(this,HomeActivity::class.java)
                        startActivity(homeIntent)
                        finish()
                    }else{
                        try {
                            throw task.exception!!
                            viewDialog.hideDialog()
                        }catch (errLogin: FirebaseAuthInvalidUserException){
                            val errorCode = errLogin.errorCode
                            btn_login.isEnabled = true

                            if(errorCode.equals("ERROR_USER_NOT_FOUND")){
                                et_loginEmail.setError(getString(R.string.err_emailNotFound))
                            }
                            viewDialog.hideDialog()
                        }catch (emailFormat: FirebaseAuthInvalidCredentialsException){
                            val err = emailFormat.errorCode
                            btn_login.isEnabled = true

                            when(err){
                                "ERROR_WRONG_PASSWORD" -> et_loginPassword.setError(getString(R.string.err_wrongPassword))
                                "ERROR_INVALID_EMAIL" -> et_loginEmail.setError(getString(R.string.err_emailNotValid))
                            }
                            viewDialog.hideDialog()
                        }catch (errConn : FirebaseNetworkException){
                            btn_login.isEnabled = true
                            val err = errConn.message
                            Log.d("TESTCONN",err)
                            viewDialog.hideDialog()
                        }catch (tomany : FirebaseTooManyRequestsException){
                            btn_login.isEnabled = false
                            startTimer()
                            viewDialog.hideDialog()
                        }
                    }
                }
        }else{
            btn_login.isEnabled = true
            et_loginPassword.setError(getString(R.string.err_passwordNull))
            viewDialog.hideDialog()
        }

    }
    fun startTimer(){
        timer = object : CountDownTimer(10000,1000){
            override fun onFinish() {
                btn_login.isEnabled = true
                btn_login.text = getString(R.string.btn_login)
            }

            override fun onTick(millisUntilFinished: Long) {
                var secondRemaining = millisUntilFinished/1000
                Log.d("COEG","TUNGGU :"+secondRemaining.toString())
                btn_login.text =  secondRemaining.toString()
            }

        }
        timer.start();
    }
    fun checkNull(variable:String) : Boolean{
        if(variable.equals("") || variable.equals(null)){
            return true
        }
        return false
    }
}