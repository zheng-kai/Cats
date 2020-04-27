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
            @Query("limit") limit: Int): Call<ArrayList<PicsBean>>

    @GET("images/search")
    fun getBreedPic(@Query("breed_ids") breedId: String,
                    @Query("order") order: String = "ASC"): Call<ArrayList<BreedDetailItem>>

    @GET("breeds")
    fun getAllBreeds():Call<ArrayList<BreedBean>>
}