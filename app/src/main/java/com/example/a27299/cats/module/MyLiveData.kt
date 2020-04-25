package com.example.a27299.cats.module

import android.arch.lifecycle.MutableLiveData

object MyLiveData {
    val mutableLiveData = MutableLiveData<ArrayList<PicsBean>>()
    suspend fun getPics(order: String = "RANDOM", type: String = "jpg,png", limit: Int = 50, category: Int? = null) {
        val response = when (category) {
            null -> {
                Service.getPics(order, type, limit).execute()
            }
            else -> {
//                Service.getPics(order, type, limit, category).execute()
                Service.getPics(order, type, limit).execute()

            }
        }
        mutableLiveData.value = mutableLiveData.value.apply {
            this?.addAll(response.body() ?: arrayListOf())
        }
    }
}