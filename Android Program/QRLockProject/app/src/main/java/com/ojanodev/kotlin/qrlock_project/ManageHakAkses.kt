package com.ojanodev.kotlin.qrlock_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_manage_hak_akses.*


class ManageHakAkses : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_hak_akses)

        val fm : FragmentManager = supportFragmentManager

        val listHA : Fragment = ListHakAksesFragment()
        val reqHA : Fragment = RequestHakAksesFragment()

        var active : Fragment = listHA

        fm.beginTransaction().add(R.id.manageHA_container,reqHA,"2").hide(reqHA).commit()
        fm.beginTransaction().add(R.id.manageHA_container,listHA, "1").commit()

        supportActionBar?.setTitle(getString(R.string.titleListHakAkses))

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.listHakAkses -> {
                    fm.beginTransaction().hide(active).show(listHA).commit()
                    active = listHA
                    supportActionBar?.setTitle(getString(R.string.titleListHakAkses))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.reqHakAkses -> {
                    fm.beginTransaction().hide(active).show(reqHA).commit()
                    active = reqHA
                    supportActionBar?.setTitle(getString(R.string.titleReqHakAkses))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }
}
