package com.ojanodev.kotlin.qrlock_project.Preference

import android.content.Context
import android.content.SharedPreferences

class LoginPreference(val context: Context){
    private val PREFS_NAME = "loginPref"
    private val LOGIN_STATS = "loginStats"
    private val USER_ID = "userID"
    private val LOGIN_TOKEN = "loginToken"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    fun checkLoginData() : Boolean{
        val state = getValueBoolean(LOGIN_STATS, false)

        if(!state){
            return false
        }
        return true
    }
    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean = false): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }
    fun getValueString(KEY_NAME: String?, defaultValue: String?) : String? {
        return sharedPref.getString(KEY_NAME,defaultValue)
    }

    fun saveBool(KEY_NAME: String, text: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, text)
        editor.apply()
    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        editor.clear()
        editor.apply()
    }
}