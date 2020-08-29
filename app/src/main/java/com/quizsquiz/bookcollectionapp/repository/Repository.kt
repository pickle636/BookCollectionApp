package com.quizsquiz.bookcollectionapp.repository

import android.util.Log
import com.quizsquiz.bookcollectionapp.database.BookDao
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.BookApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
@ExperimentalCoroutinesApi
class Repository(private val bookDao: BookDao?, private val apiService: BookApiService) {

    private var allBookList = mutableListOf<Book>()

    private val allBooksStateFlow = MutableStateFlow(allBookList.toList())


    private suspend fun addData(bookList: List<Book>) {
        bookDao?.insertBooks(bookList)
        allBooksStateFlow.value = bookList.toList()
    }



    suspend fun getAllBooks(): StateFlow<List<Book>> {
        allBookList = bookDao?.getAllBooks()!!.toMutableList()
        allBooksStateFlow.value = allBookList.toList()
        return allBooksStateFlow
    }

    private suspend fun deleteAllData() {
        bookDao?.deleteAll()
    }

    suspend fun loadAndPutInDatabase() {

        val list = apiService.getBooks()
        Log.e("log", "$list")
        deleteAllData()
        addData(list)
    }

    suspend fun uploadBookOnServer(book: Book) {
        val response = apiService.postBook(book)
        bookDao?.insertBook(response)

    }
}