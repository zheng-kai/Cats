package com.example.a27299.cats.module

import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object MyLiveData {
    val mutableLiveData = MutableLiveData<ArrayList<PicsBean>>()
    suspend fun getPics(order: String = "RANDOM", type: Array<String> = arrayOf("jpg","png"), limit: Int = 50, category: Int? = null) {
        val response = when (category) {
            null -> {
                Module.service.getPics(order, type, limit).execute()
            }
            else -> {
//                Service.getPics(order, type, limit, category).execute()
                Module.service.getPics(order, type, limit).execute()

            }
        }
        GlobalScope.launch(Main) {

            mutableLiveData.value = ArrayList<PicsBean>().apply {
                addAll(mutableLiveData.value?: arrayListOf())
                addAll(response.body()?: arrayListOf())
            }
        }


    }
}