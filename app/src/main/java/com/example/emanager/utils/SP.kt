package com.example.emanager.utils

import android.content.Context
import android.content.SharedPreferences

class SP private constructor(){
    companion object{
        private val SP_NAME = "MyPref"
        @Volatile
        private var sharedPreferences : SharedPreferences? = null

        fun getSharedPreferenceInstance(context: Context) : SharedPreferences {
            return sharedPreferences ?: synchronized(this){
                val instance = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE)
                sharedPreferences = instance
                instance
            }
        }


        fun getEditor(context: Context): SharedPreferences.Editor {
            return getSharedPreferenceInstance(context).edit()
        }

        fun clearData(context: Context){
            getEditor(context).clear().apply()
        }
    }
}