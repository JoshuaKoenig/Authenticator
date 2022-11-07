package com.koenig.loginapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // THE ACTION KEY
    private val action = "com.koenig.loginapp.CODE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(getString(R.string.shared_prefs_key), Context.MODE_PRIVATE)

        // IF NO ACCOUNT EXISTS, CALL UP THE CREATE ACCOUNT ACTIVITY
        if(!sharedPreferences.getBoolean(getString(R.string.has_account_key), false))
        {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        // THE LOGIN BUTTON
        val loginButton: Button = findViewById(R.id.buttonLogin)

        // THE USERNAME INPUT
        val userNameInput: EditText = findViewById(R.id.textUserName)

        // THE PASSWORD INPUT
        val passwordInput: EditText = findViewById(R.id.textPassword)

        // VALIDATE THE CREDENTIALS ENTERED
        loginButton.setOnClickListener {
            validateCredentials(userNameInput.text.toString(), passwordInput.text.toString())
        }
    }

    private fun validateCredentials(userNameInput: String, passwordInput: String)
    {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(getString(R.string.shared_prefs_key), Context.MODE_PRIVATE)

        // GET THE USER'S CREDENTIALS
        val userName = sharedPreferences.getString(getString(R.string.userName_key), "")
        val password = sharedPreferences.getString(getString(R.string.pw_key), "")

        // COMPARE USER CREDENTIALS WITH USER INPUT
        if (userName == userNameInput && password == passwordInput)
        {
            // GENERATE A RANDOM SIX-DIGIT NUMBER
            val codeAsNumber = Random.nextInt(999999 - 100000) + 100000
            val sixDigitCode = String.format("%06d", codeAsNumber)

            // TEMPORARILY SAVE THE SENT CODE
            val editor = sharedPreferences.edit()
            editor.putString(getString(R.string.temporary_code_key), sixDigitCode)
            editor.apply()

            // SEND BROADCAST TO CODE GENERATOR WITH SIX-DIGIT NUMBER
            sendCodeBroadcastWithPermission(sixDigitCode)

            // START THE ENTER-CODE-ACTIVITY
            val intent = Intent(this, EnterCodeActivity::class.java)
            startActivity(intent)
        }
        else
        {
            // WRONG CREDENTIALS ENTERED
            Toast.makeText(this, "WRONG CREDENTIALS", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendCodeBroadcast(sixDigitCode: String)
    {
        Intent().also { intent ->
            intent.action = action
            intent.putExtra("Code", sixDigitCode)
            sendBroadcast(intent)
        }
    }

    private fun sendCodeBroadcastWithPermission(sixDigitCode: String)
    {
        Intent().also { intent ->
            intent.action = action
            intent.putExtra("Code", sixDigitCode)
            sendBroadcast(intent, "com.koenig.loginapp.permission.receive")
        }
    }
}