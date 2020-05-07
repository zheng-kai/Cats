package com.example.a27299.cats.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.a27299.cats.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        iv_register_back.setOnClickListener {
            onBackPressed()
        }
        btn_register.setOnClickListener {
            Toast.makeText(this,"待开发功能", Toast.LENGTH_LONG).show()

        }
    }
}