package com.quizsquiz.bookcollectionapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.database.BookRepository
import com.quizsquiz.bookcollectionapp.viewmodels.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val repository: BookRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(repository) as T
        }
        else {
            throw IllegalArgumentException()
        }
    }
}