package com.quizsquiz.bookcollectionapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.quizsquiz.bookcollectionapp.models.Book

@Dao
interface BookDao {
    @Insert
    suspend fun insertBook(book: Book) : Long

    @Update
    suspend fun updateBook(book: Book) : Int

    @Delete
    suspend fun deleteBook(book: Book) : Int

    @Query("DELETE FROM books")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM books")
    fun getAllBooks():LiveData<List<Book>>
}