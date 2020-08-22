package com.quizsquiz.bookcollectionapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.adapters.BookItemAdapter
import com.quizsquiz.bookcollectionapp.database.BookDatabase
import com.quizsquiz.bookcollectionapp.databinding.ActivityMainBinding
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.util.MainViewModelFactory
import com.quizsquiz.bookcollectionapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var viewModel: MainViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = BookDatabase.getDatabase(application)?.getBookDao()
        var viewModelFactory = MainViewModelFactory(Repository(dao))
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        //class BookItemAdapter(private val clickListener:(Book) -> Unit) :

        var b = fun(book: Book) {
            listItemClicked(book)
        }

        val adapter = BookItemAdapter(b)

        binding.adapter = adapter
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel!!.booksFromNetwork.observe(this, { bookList ->
            adapter.onChange(bookList)
        })

        fab_create.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }

    private fun listItemClicked(book: Book) {
        viewModel?.deleteBookByTouch(book)
    }
}

