package com.quizsquiz.bookcollectionapp.network

import com.quizsquiz.bookcollectionapp.models.Book
import retrofit2.http.GET
import retrofit2.http.POST

interface BookApiService {
    @GET("/api/books")
    suspend fun getBooks(): List<Book>
    @POST("/api/books/create")
    suspend fun postBook(): Book
}