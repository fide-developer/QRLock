package com.ojanodev.kotlin.qrlock_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ojanodev.kotlin.qrlock_project.Preference.LoginPreference
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    val SCAN_REQUEST = 101
    lateinit var loginPreference: LoginPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loginPreference = LoginPreference(this)

        val actBar = supportActionBar
        actBar?.setTitle(getString(R.string.qrLock))

        initButton(loginPreference.getValueBoolean("isAdmin"))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                loginPreference.saveBool("isAdmin",false)
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Membaca file menu dan menambahkan isinya ke action bar jika ada.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    private fun initButton(isAdmin:Boolean){
        btn_openPintu.setOnClickListener {
            val scanACt = Intent(this,ScanActivity::class.java)
            startActivity(scanACt)
        }
        if(isAdmin){
            btn_hakAkses.text = getString(R.string.btn_manageHakAkses)
            textView6.text = "Administrator"
            btn_hakAkses.setOnClickListener {
                val intentManageHA = Intent(this,ManageHakAkses::class.java)
                startActivity(intentManageHA)
            }
        }else{
            textView6.text = "Selamat Datang"
            btn_hakAkses.setOnClickListener {
                val intentHakAkses = Intent(this,LihatHakAkses::class.java)
                startActivity(intentHakAkses)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SCAN_REQUEST){
            val qr_value = data?.getStringExtra("qr_value")
            openPintu(qr_value)
        }
    }

    private fun openPintu(qrValue: String?) {
        Log.d("FUCKER",qrValue)
    }
}
