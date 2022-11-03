package com.koenig.loginapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EnterCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_code)

        // THE USERS CODE INPUT
        val codeInput: EditText = findViewById(R.id.textCode)

        // THE CONTINUE BUTTON
        val buttonContinue: Button = findViewById(R.id.buttonContinue)

        // VALIDATE ENTERED CODE
        buttonContinue.setOnClickListener {
            validateCode(codeInput.text.toString())
        }
    }

    private fun validateCode(codeInput: String)
    {
        // GET THE CODE
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(getString(R.string.shared_prefs_key), Context.MODE_PRIVATE)
        val code = sharedPreferences.getString(getString(R.string.temporary_code_key), "")

        // VALIDATION OF THE USER'S CODE INPUT
        if (code == codeInput)
        {
            // CORRECT CODE ENTERED => START AUTH ACTIVITY
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
        else
        {
            // WRONG CODE ENTERED
            Toast.makeText(this, "Wrong Code", Toast.LENGTH_LONG).show()
        }
    }
}