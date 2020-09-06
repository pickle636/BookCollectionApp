package com.quizsquiz.bookcollectionapp.repository

import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.BookApiService
import com.quizsquiz.bookcollectionapp.room.BookDatabase
import com.quizsquiz.bookcollectionapp.room.Dao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class Repository @Inject constructor(private val apiService: BookApiService, private var dao: Dao) {

    private var allBookList = mutableListOf<Book>()
    private val allBooksStateFlow = MutableStateFlow(allBookList.toList())


    private suspend fun addData(bookList: List<Book>) {
        dao.insertBooks(bookList)
        allBooksStateFlow.value = bookList.toList()
    }

    suspend fun getAllBooks(): StateFlow<List<Book>> {
        allBookList = dao.getAllBooks().toMutableList()
        allBooksStateFlow.value = allBookList.toList()
        return allBooksStateFlow
    }

    private suspend fun deleteAllDataFromDB() {
        dao.deleteAll()
    }

    suspend fun loadAndPutInDatabase() {
        val list = apiService.getBooks()
        deleteAllDataFromDB()
        addData(list)
    }

    suspend fun uploadBookOnServer(book: Book) {
        val response = apiService.postBook(book)
        dao.insertBook(response)

    }
    suspend fun deleteBookFromServer(book: Book) {
        apiService.deleteBook(book.id)
        dao.deleteBook(book)
    }

    suspend fun updateBookFromServer(book: Book) {
        apiService.updateBook(book.id, book)
        dao.updateBook(book)
    }

}