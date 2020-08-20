package com.quizsquiz.bookcollectionapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.database.BookRepository
import com.quizsquiz.bookcollectionapp.viewmodels.CreateViewModel
import java.lang.IllegalArgumentException

class CreateViewModelFactory(private val repository: BookRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CreateViewModel::class.java)){
            CreateViewModel(repository) as T
        }
        else {
            throw IllegalArgumentException()
        }
    }
}