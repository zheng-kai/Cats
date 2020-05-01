package com.example.a27299.cats

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.a27299.cats.home.HomeActivity
import com.example.a27299.cats.module.Module
import com.example.a27299.cats.module.ServiceApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.WHITE
        }
        startActivity(Intent(this, HomeActivity::class.java))
//        startActivity(Intent(this, LoginActivity::class.java))

        GlobalScope.launch {
            Module.saveCategories(ServiceApi.service.getCategories().execute().body()?: arrayListOf())
        }
        finish()
    }
}