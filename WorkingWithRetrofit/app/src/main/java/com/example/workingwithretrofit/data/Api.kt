package com.example.workingwithretrofit.data

import com.example.workingwithretrofit.data.model.Products
import retrofit2.http.GET

interface Api {//calling api

    @GET("products")//this the path it will take using the BASE_URL
    suspend fun getProductsList():Products

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}