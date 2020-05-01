package com.example.a27299.cats.module

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    /**
     * order : RANDOM ASC DESC
     *
     */


    @GET("images/search")
    fun getPics(
            @Query("order") order: String,
            @Query("mime_types") type: Array<String>,
            @Query("limit") limit: Int,
            @Query("category_id") category_ids: Array<Int>? = null): Call<ArrayList<PicsBean>>

    @GET("images/search")
    fun getBreedPic(@Query("breed_ids") breedId: String,
                    @Query("order") order: String = "ASC"): Call<ArrayList<BreedDetailItem>>

    @GET("breeds")
    fun getAllBreeds(): Call<ArrayList<BreedBean>>

    @GET("categories")
    fun getCategories(): Call<ArrayList<Category>>
}