package com.firstkotlin.mauriciofeijo.firstkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.firstkotlin.mauriciofeijo.firstkotlin.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText.setText("admin")
        passwordEditText.setText("password")

        login_button.setOnClickListener {
            if (usernameEditText.text.toString() == "admin" && passwordEditText.text.toString() == "password") {
                val intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Usuário e/ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
