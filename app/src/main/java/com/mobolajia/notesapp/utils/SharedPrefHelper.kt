package com.mobolajia.notesapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {

    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("BOLAJI_NOTES_APP_PREFERENCES", 0)

    fun saveInt(key : String, value : Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int) : Int {
        return sharedPreferences.getInt(key, defValue)
    }

    fun clearSharedPreferences(){
        sharedPreferences.edit().clear().apply()
    }

}