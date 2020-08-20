package com.quizsquiz.bookcollectionapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.database.BookRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: BookRepository) : ViewModel() {
    var books: LiveData<List<Book>>? = repository.books

    fun deleteBookByTouch(book: Book) {
        viewModelScope.launch {
            val result = repository.delete(book)
            Log.e("result", "$result")
        }
    }

}
