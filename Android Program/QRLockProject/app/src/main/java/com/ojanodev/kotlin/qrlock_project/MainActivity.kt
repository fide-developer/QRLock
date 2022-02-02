package com.ojanodev.kotlin.qrlock_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ojanodev.kotlin.qrlock_project.Preference.LoginPreference
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        tv_statusFirstLoad.text = getString(R.string.stats_checkData)
        checkLogin()
    }

    private fun checkLogin() {
        val loginPreference:LoginPreference= LoginPreference(this)
        val sudahLogin = loginPreference.checkLoginData()
        val handler = Handler()
        val user = FirebaseAuth.getInstance().currentUser
        val loginIntent: Intent = Intent(this, LoginActivity::class.java)
        val homeIntent: Intent = Intent(this, HomeActivity::class.java)
        handler.postDelayed({
            if(sudahLogin && user!=null){
                tv_statusFirstLoad.text = getString(R.string.stats_loadData)
                //do some shit to load data here
                startActivity(homeIntent)
            }else{
                tv_statusFirstLoad.text = getString(R.string.stats_redirectLogin)
                handler.postDelayed({
                    //intent to login
                    startActivity(loginIntent)
                },500)
            }
        },1000)
    }
}
