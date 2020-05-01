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
    val categoryIds = ArrayList<Int>()
    fun saveCategories(list: ArrayList<Category>) = Hawk.put("categories", list)
    fun getCategories(): ArrayList<Category>? = Hawk.get("categories")
    fun init() {
        selectedLiveData.value = arrayListOf()
        mutableLiveData.value = arrayListOf()
        speciesLiveData.value = arrayListOf()
    }

    fun addSelected(category: Category) {
        selectedLiveData.value = selectedLiveData.value?.apply {
            add(category.name)
        }
        categoryIds.add(category.id)
    }

    fun remove(category: Category) {
        selectedLiveData.value = selectedLiveData.value?.apply {
            remove(category.name)
        }
        categoryIds.remove(category.id)

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
        val response = ServiceApi.service.getPics(order, type, limit, categoryIds.toTypedArray()).execute()


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