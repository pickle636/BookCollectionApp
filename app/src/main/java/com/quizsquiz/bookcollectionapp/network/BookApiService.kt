package com.quizsquiz.bookcollectionapp.network

import com.quizsquiz.bookcollectionapp.models.Book
import retrofit2.http.GET

interface BookApiService {
    @GET("/api/books")
    suspend fun getBooks(): List<Book>
}