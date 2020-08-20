package com.quizsquiz.bookcollectionapp.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.database.BookRepository
import kotlinx.coroutines.launch


class CreateViewModel(var repository: BookRepository) : ViewModel() {

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