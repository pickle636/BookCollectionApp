package com.quizsquiz.bookcollectionapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.repository.Repository
import kotlinx.coroutines.launch


class CreateViewModel(var repository: Repository) : ViewModel() {

    var book: MutableLiveData<Book> = MutableLiveData()

    var description: MutableLiveData<String> = MutableLiveData()
    var title: MutableLiveData<String> = MutableLiveData()
    var author: MutableLiveData<String> = MutableLiveData()
    var published: MutableLiveData<Int> = MutableLiveData()


    fun insertBook() {
        val book = Book(0, title.value!!, description.value!!, author.value!!, published.value!!)
        viewModelScope.launch {
            repository.insert(book)
        }

    }
}