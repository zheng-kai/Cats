package com.example.a27299.cats.module

import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Module {
    val mutableLiveData = MutableLiveData<ArrayList<PicsBean>>()
    val speciesLiveData = MutableLiveData<ArrayList<BreedDetailItem>>()
    fun init(){
        mutableLiveData.value = arrayListOf()
        speciesLiveData.value = arrayListOf()
    }
    suspend fun getAllBreeds() = ServiceApi.service.getAllBreeds()
    suspend fun addBreedDetail(id:String){
        val detail = ServiceApi.service.getBreedPic(id).execute().body()
        detail?.let {
            if(it.size>0){
                speciesLiveData.value = speciesLiveData.value?.apply {
                    this.addAll(it)
                }
            }
        }
    }
    fun clear() {
        GlobalScope.launch(Main) {
            mutableLiveData.value = ArrayList()
        }
    }

    suspend fun getPics(order: String = "RANDOM", type: Array<String> = arrayOf("jpg", "png", "gif"), limit: Int = 50, category: Int? = null) {
        val response = when (category) {
            null -> {
                ServiceApi.service.getPics(order, type, limit).execute()
            }
            else -> {
//                Service.getPics(order, type, limit, category).execute()
                ServiceApi.service.getPics(order, type, limit).execute()

            }
        }
        GlobalScope.launch(Main) {

//            mutableLiveData.value = ArrayList<PicsBean>().apply {
//                addAll(mutableLiveData.value ?: arrayListOf())
//                addAll(response.body() ?: arrayListOf())
//            }

            mutableLiveData.value = mutableLiveData.value?.apply {
                addAll(response.body() ?: arrayListOf())
            }
        }
    }

}