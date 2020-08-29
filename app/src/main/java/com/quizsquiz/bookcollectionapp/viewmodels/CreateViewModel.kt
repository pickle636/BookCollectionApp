package com.quizsquiz.bookcollectionapp.viewmodels

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.ui.CreateActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


class CreateViewModel @ExperimentalCoroutinesApi constructor(var repository: Repository) : ViewModel() {
    var isConnected: ObservableBoolean = ObservableBoolean(false)
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
        }
    }
}