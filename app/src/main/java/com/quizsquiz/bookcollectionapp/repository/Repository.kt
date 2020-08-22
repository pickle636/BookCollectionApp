package com.quizsquiz.bookcollectionapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quizsquiz.bookcollectionapp.database.BookDao
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.Controller
import kotlinx.coroutines.*

class Repository(private val dao: BookDao?) {

    val booksFromNetwork = Controller.getBooksFromNetwork()
    var booksFromDB = dao?.getAllBooks()


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