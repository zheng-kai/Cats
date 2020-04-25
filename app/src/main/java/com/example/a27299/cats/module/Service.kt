package com.example.a27299.cats.module

import retrofit2.Call
import retrofit2.http.GET

interface Service {

    @GET("images/search")
    fun getPics(): Call<ArrayList<PicsBean>>
    
    companion object : Service by Module()
}