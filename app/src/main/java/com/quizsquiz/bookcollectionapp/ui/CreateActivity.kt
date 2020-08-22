package com.quizsquiz.bookcollectionapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.database.BookDatabase
import com.quizsquiz.bookcollectionapp.databinding.ActivityCreateBinding
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.util.CreateViewModelFactory
import com.quizsquiz.bookcollectionapp.viewmodels.CreateViewModel

class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = BookDatabase.getDatabase(application)?.getBookDao()
        val viewModelFactory = CreateViewModelFactory(Repository(dao))
        val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java) }

        val binding: ActivityCreateBinding = DataBindingUtil.setContentView(this, R.layout.activity_create)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel



    }
}