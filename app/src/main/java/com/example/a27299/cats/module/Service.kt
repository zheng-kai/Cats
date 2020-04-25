package com.example.a27299.cats.module
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    /**
     * order : RANDOM ASC DESC
     *
     */
//    @GET("images/search")
//    suspend fun getPics(
//            @Query("order") order: String = "RANDOM",
//            @Query("mime_type") type: String = "jpg,png",
//            @Query("limit") limit: Int = 50,
//            @Query("category_ids") category: Int): Call<ArrayList<PicsBean>>

    @GET("images/search")
    suspend fun getPics(
            @Query("order") order: String = "RANDOM",
            @Query("mime_type") type: String = "jpg,png",
            @Query("limit") limit: Int = 50): Call<ArrayList<PicsBean>>

    companion object : Service by Module()
}