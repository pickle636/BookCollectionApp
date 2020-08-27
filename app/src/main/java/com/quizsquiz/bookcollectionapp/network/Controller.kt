package com.quizsquiz.bookcollectionapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Controller {
    private const val BASE_URL: String = "https://spring-boot-mysql-server-part0.herokuapp.com"
    fun getApiArguments() : BookApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(BookApiService::class.java)
    }
}