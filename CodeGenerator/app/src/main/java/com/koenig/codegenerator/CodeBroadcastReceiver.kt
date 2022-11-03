package com.koenig.codegenerator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

class CodeBroadcastReceiver : BroadcastReceiver()
{
    private val sharedPreferencesKey = "SHARED_PREFERENCES_KEY"
    private val codeKey = "TEMP_CODE_KEY"

    override fun onReceive(context: Context?, intent: Intent?)
    {
        // THE RECEIVED CODE
        val receivedCode = intent!!.extras!!.getString("Code")

        // SAVE THE CODE TEMPORARILY
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(codeKey, receivedCode.toString())
        editor.apply()
    }
}