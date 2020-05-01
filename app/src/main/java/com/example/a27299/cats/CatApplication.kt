package com.example.a27299.cats

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder

class CatApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
    }
}