package com.example.a27299.cats.module

import android.arch.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Module {
    val mutableLiveData = MutableLiveData<ArrayList<PicsBean>>()
    val speciesLiveData = MutableLiveData<ArrayList<BreedDetailItem>>()
    val selectedLiveData = MutableLiveData<ArrayList<String>>()
    fun init(){
        selectedLiveData.value = arrayListOf()
        mutableLiveData.value = arrayListOf()
        speciesLiveData.value = arrayListOf()
    }
    fun addSelected(str:String){
        selectedLiveData.value?.add(str)
    }
    fun remove(str: String){
        selectedLiveData.value?.remove(str)
    }
    suspend fun getAllBreeds() = ServiceApi.service.getAllBreeds()
    suspend fun addBreedDetail(ids:MutableList<String>){
        val list = ArrayList<BreedDetailItem>()
        for (id in ids){
            val detail = ServiceApi.service.getBreedPic(id).execute().body()
            detail?.apply {
                list.add(get(0))
            }
        }
        list.let {
            if(it.size>0){
                GlobalScope.launch(Main) {
                    speciesLiveData.value = speciesLiveData.value?.apply {
                        this.addAll(it)
                    }
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