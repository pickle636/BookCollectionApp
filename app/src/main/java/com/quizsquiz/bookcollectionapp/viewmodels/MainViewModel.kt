package com.quizsquiz.bookcollectionapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.Controller
import com.quizsquiz.bookcollectionapp.repository.Repository
import kotlinx.coroutines.*

class MainViewModel(private val repository: Repository) : ViewModel() {
    var booksFromDB = repository.booksFromDB
    var booksFromNetwork = repository.booksFromNetwork

//fun getAllBooks(): LiveData<List<Book>> {
//
//    viewModelScope.launch {
//
//        return@launch
//    }
//
//}




    fun deleteBookByTouch(book: Book) {
        viewModelScope.launch {
            val result = repository.delete(book)
            Log.e("result", "$result")
        }
    }
}



