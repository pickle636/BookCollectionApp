package com.quizsquiz.bookcollectionapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


class CreateViewModel @ExperimentalCoroutinesApi
@ViewModelInject constructor(var repository: Repository) : ViewModel() {
    var isConnected: ObservableBoolean = ObservableBoolean(false)
    var isLoadingFinished: MutableLiveData<Boolean> = MutableLiveData(false)

    var book: MutableLiveData<Book> = MutableLiveData()

    var description: MutableLiveData<String> = MutableLiveData()
    var title: MutableLiveData<String> = MutableLiveData()
    var author: MutableLiveData<String> = MutableLiveData()
    var published: MutableLiveData<Int> = MutableLiveData()


    @ExperimentalCoroutinesApi
    fun insertBook() {
        val book = Book(0, title.value!!, description.value!!, author.value!!, published.value!!)
        viewModelScope.launch {
            repository.uploadBookOnServer(book)
            isLoadingFinished.value = true
        }
        isLoadingFinished.value = false

    }
    @ExperimentalCoroutinesApi
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBookFromServer(book)
            isLoadingFinished.value = true
        }
        isLoadingFinished.value = false
    }

    @ExperimentalCoroutinesApi
    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBookFromServer(book)
            isLoadingFinished.value = true
        }
        isLoadingFinished.value = false
    }
}