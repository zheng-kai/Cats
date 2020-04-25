package com.example.a27299.cats.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.a27299.cats.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        iv_login_back.setOnClickListener {
            onBackPressed()
        }
        tv_login_to_register.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
        btn_login.setOnClickListener {
            Toast.makeText(this,"登录",Toast.LENGTH_LONG).show()
        }
    }
}