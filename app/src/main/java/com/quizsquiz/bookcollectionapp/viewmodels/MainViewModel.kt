package com.quizsquiz.bookcollectionapp.viewmodels

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: Repository) : ViewModel() {
    private var _bookLiveData = MutableLiveData<List<Book>>()
    val bookLiveData: LiveData<List<Book>> = _bookLiveData

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("TAG", "CoroutineExceptionHandler got", exception)
    }

    private val scope = viewModelScope + handler + Dispatchers.Default


    @InternalCoroutinesApi
    fun loadBooksFromServer() {
        scope.launch {
            repository.loadAndPutInDatabase()

        }
        getAllBooks()
    }


    @InternalCoroutinesApi
    fun getAllBooks() {
        scope.launch {
            repository.getAllBooks().collect { it ->
                _bookLiveData.postValue(it)
            }
        }
    }
}



