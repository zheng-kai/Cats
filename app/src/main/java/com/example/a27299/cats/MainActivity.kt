package com.example.a27299.cats

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.a27299.cats.home.HomeActivity
import com.example.a27299.cats.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = applicationContext.getSharedPreferences("cats", Context.MODE_PRIVATE)
        val login = sp.getBoolean("login",false)
        if(login){
            startActivity(Intent(this,HomeActivity::class.java))
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}
