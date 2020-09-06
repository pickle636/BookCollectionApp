package com.quizsquiz.bookcollectionapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.repository.Repository
import kotlinx.coroutines.*


class CreateViewModel @ExperimentalCoroutinesApi
@ViewModelInject constructor(var repository: Repository) : ViewModel() {
    var isConnected: ObservableBoolean = ObservableBoolean(false)
    var isLoadingActive: MutableLiveData<Boolean> = MutableLiveData(viewModelScope.isActive)
    var book = Book(0, "", "", "", null)



    @ExperimentalCoroutinesApi
    fun insertBook() {
        viewModelScope.launch {
            repository.uploadBookOnServer(book)
        }

    }
    @ExperimentalCoroutinesApi
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBookFromServer(book)
        }
    }

    @ExperimentalCoroutinesApi
    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBookFromServer(book)
        }
    }
//    fun checkFields(book: Book): Boolean {
//        return when {
//            book.title.isNullOrEmpty() ->
//        }
//    }
}