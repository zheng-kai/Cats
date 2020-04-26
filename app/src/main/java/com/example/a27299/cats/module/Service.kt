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
            @Query("order") order: String ,
            @Query("mime_type") type: Array<String> ,
            @Query("limit") limit:Int): Call<ArrayList<PicsBean>>

}