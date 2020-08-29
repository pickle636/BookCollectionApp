package com.quizsquiz.bookcollectionapp.network

import com.quizsquiz.bookcollectionapp.models.Book
import okhttp3.Response
import retrofit2.http.*

interface BookApiService {
    @GET("/api/books")
    suspend fun getBooks(): List<Book>
    @POST("/api/books/create")
    suspend fun postBook(@Body book: Book): Book
    @DELETE("/api/books/{id}")
    suspend fun deleteBook(@Path ("id") id: Int): Response
}