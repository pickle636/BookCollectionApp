package com.quizsquiz.bookcollectionapp.database

import com.quizsquiz.bookcollectionapp.models.Book

class BookRepository(private val dao: BookDao?) {

    val books = dao?.getAllBooks()

    suspend fun insert(book: Book): Long {
        return dao!!.insertBook(book)

    }

    suspend fun update(book: Book): Int {

        return dao!!.updateBook(book)

    }

    suspend fun delete(book: Book): Int {

        return dao!!.deleteBook(book)

    }

    suspend fun deleteAll(): Int {

        return dao!!.deleteAll()

    }

}