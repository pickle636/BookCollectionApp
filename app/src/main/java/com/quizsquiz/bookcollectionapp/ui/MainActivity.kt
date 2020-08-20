package com.quizsquiz.bookcollectionapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.adapters.BookItemAdapter
import com.quizsquiz.bookcollectionapp.database.BookDatabase
import com.quizsquiz.bookcollectionapp.databinding.ActivityMainBinding
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.database.BookRepository
import com.quizsquiz.bookcollectionapp.util.MainViewModelFactory
import com.quizsquiz.bookcollectionapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = BookDatabase.getDatabase(application)?.getBookDao()
        var viewModelFactory = MainViewModelFactory(BookRepository(dao))
        val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java) }

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = BookItemAdapter(this, mutableListOf())

        binding.adapter = adapter
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.books?.observe(this, { bookList ->
            adapter.onChange(bookList)
        })

        fab_create.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }

}