package com.quizsquiz.bookcollectionapp.room
import androidx.room.*
import androidx.room.Dao
import com.quizsquiz.bookcollectionapp.models.Book

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(list: List<Book>): List<Long>

    @Insert
    suspend fun insertBook(book: Book) : Long

    @Update
    suspend fun updateBook(book: Book) : Int

    @Delete
    suspend fun deleteBook(book: Book) : Int

    @Query("DELETE FROM books")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Book>
}