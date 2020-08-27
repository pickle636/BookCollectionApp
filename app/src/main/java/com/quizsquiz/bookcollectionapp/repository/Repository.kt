package com.quizsquiz.bookcollectionapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quizsquiz.bookcollectionapp.database.BookDao
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.BookApiService
import com.quizsquiz.bookcollectionapp.ui.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
@ExperimentalCoroutinesApi
class Repository(private val bookDao: BookDao?, private val apiService: BookApiService) {

    private var allBookList = mutableListOf<Book>()

    private val allBooksStateFlow = MutableStateFlow(allBookList.toList())


    private suspend fun addData(anecdoteList: List<Book>) {
        bookDao?.insertBooks(anecdoteList)
        allBooksStateFlow.value = anecdoteList.toList()
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

}