package com.quizsquiz.bookcollectionapp.repository

import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.BookApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class Repository @Inject constructor(private val apiService: BookApiService) {

    private var allBookList = mutableListOf<Book>()
    private val allBooksStateFlow = MutableStateFlow(allBookList.toList())


    suspend fun loadBooks(): MutableStateFlow<List<Book>> {
        val list = apiService.getBooks()
        allBookList = list.toMutableList()
        allBooksStateFlow.value = allBookList
        return allBooksStateFlow
    }

    suspend fun uploadBookOnServer(book: Book) {
        apiService.postBook(book)

    }
    suspend fun deleteBookFromServer(book: Book) {
        apiService.deleteBook(book.id)
    }

    suspend fun updateBookFromServer(book: Book) {
        apiService.updateBook(book.id, book)
    }

}