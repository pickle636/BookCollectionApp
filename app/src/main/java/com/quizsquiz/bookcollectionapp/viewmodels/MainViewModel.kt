package com.quizsquiz.bookcollectionapp.viewmodels

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {
    var isConnected: ObservableBoolean = ObservableBoolean(false)
    private var _bookLiveData = MutableLiveData<List<Book>>()
    val bookLiveData: LiveData<List<Book>> = _bookLiveData

    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("TAG", "CoroutineExceptionHandler got", exception)
    }

    private val scope = viewModelScope + handler + Dispatchers.Default


    @InternalCoroutinesApi
    fun loadBooksFromServer() {
        scope.launch {
            repository.loadBooks().collect { it ->
                _bookLiveData.postValue(it)
            }
        }
    }


}



