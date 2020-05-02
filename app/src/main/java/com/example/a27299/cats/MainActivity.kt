package com.example.a27299.cats

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.a27299.cats.home.HomeActivity
import com.example.a27299.cats.module.Module
import com.example.a27299.cats.module.ServiceApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.WHITE
        }
        startActivity(Intent(this, HomeActivity::class.java))

        GlobalScope.launch(IO) {
            val data = ServiceApi.service.getCategories().execute().body()
            data?.let {
                if (it != Module.getCategories()) {
                    Module.saveCategories(it)
                }
            }
        }
        finish()
    }
}