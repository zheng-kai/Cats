package com.example.a27299.cats.module

import android.arch.lifecycle.MutableLiveData
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Module {
    val mutableLiveData = MutableLiveData<ArrayList<PicsBean>>()
    val speciesLiveData = MutableLiveData<ArrayList<BreedDetailItem>>()
    val selectedLiveData = MutableLiveData<ArrayList<String>>()
    val category_ids = ArrayList<Int>()
    fun saveCategories(list: ArrayList<Category>) = Hawk.put("categories", list)
    fun getCategories(): ArrayList<Category>? = Hawk.get("categories")
    fun init() {
        selectedLiveData.value = arrayListOf()
        mutableLiveData.value = arrayListOf()
        speciesLiveData.value = arrayListOf()
    }

    fun addSelected(str: String) {
        selectedLiveData.value = selectedLiveData.value?.apply {
            add(str)
        }
    }

    fun remove(str: String) {
        selectedLiveData.value = selectedLiveData.value?.apply {
            remove(str)
        }
    }

    suspend fun getAllBreeds() = ServiceApi.service.getAllBreeds()
    suspend fun addBreedDetail(ids: MutableList<String>) {
        val list = ArrayList<BreedDetailItem>()
        for (id in ids) {
            val detail = ServiceApi.service.getBreedPic(id).execute().body()
            detail?.apply {
                list.add(get(0))
            }
        }
        list.let {
            if (it.size > 0) {
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

    suspend fun getPics(order: String = "RANDOM", type: Array<String> = arrayOf("jpg", "png", "gif"), limit: Int = 50, category: Array<Int>? = null) {
        val response = when (category) {
            null -> {
                ServiceApi.service.getPics(order, type, limit).execute()
            }
            else -> {
                ServiceApi.service.getPics(order, type, limit,category).execute()

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