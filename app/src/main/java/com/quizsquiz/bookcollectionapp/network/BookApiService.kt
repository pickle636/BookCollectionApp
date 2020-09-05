package com.quizsquiz.bookcollectionapp.network

import com.quizsquiz.bookcollectionapp.models.Book
import okhttp3.ResponseBody
import retrofit2.http.*

interface BookApiService {
    @GET("/api/books")
    suspend fun getBooks(): List<Book>
    @POST("/api/books/create")
    suspend fun postBook(@Body book: Book): Book
    @DELETE("/api/books/{id}")
    suspend fun deleteBook(@Path ("id") id: Int): ResponseBody
    @PUT("/api/books/{id}")
    suspend fun updateBook(@Path ("id") id: Int, @Body book: Book): ResponseBody
}