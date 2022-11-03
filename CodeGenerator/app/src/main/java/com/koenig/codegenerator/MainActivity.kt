package com.koenig.codegenerator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val sharedPreferencesKey = "SHARED_PREFERENCES_KEY"
    private val codeKey = "TEMP_CODE_KEY"
    private val action = "com.koenig.loginapp.CODE"
    private val br: BroadcastReceiver = CodeBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // REGISTER THE BROADCAST RECEIVER
        val filter = IntentFilter(action)
        val receiverFlag = ContextCompat.RECEIVER_EXPORTED
        ContextCompat.registerReceiver(this, br, filter, receiverFlag)

        // GET THE TEMPORARY CODE
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)

        // SHOW THE CODE
        val code = sharedPreferences.getString(codeKey, "No Code Yet ...")
        val codeText: TextView = findViewById(R.id.Code)
        codeText.text = code
        codeText.underline()
    }

    // SHOW NEW CODE IMMEDIATELY
    override fun onResume() {
        super.onResume()
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val code = sharedPreferences.getString(codeKey, "No Code Yet ...")
        val codeText: TextView = findViewById(R.id.Code)
        codeText.text = code
    }

    // UNREGISTER BROADCAST RECEIVER WHEN APP DESTROYED
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }

    fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}