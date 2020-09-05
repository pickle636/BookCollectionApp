package com.quizsquiz.bookcollectionapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.database.BookDatabase
import com.quizsquiz.bookcollectionapp.databinding.ActivityCreateBinding
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.Controller
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.util.CreateViewModelFactory
import com.quizsquiz.bookcollectionapp.viewmodels.CreateViewModel
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CreateActivity : BaseActivity() {
    private lateinit var viewModel: CreateViewModel
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = BookDatabase.getDatabase(application)?.getBookDao()
        val viewModelFactory = CreateViewModelFactory(Repository(dao, Controller.getApiArguments()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java)
        val binding: ActivityCreateBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_create
        )
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        checkConnectionInCreateActivity(viewModel)

        val book = intent.getParcelableExtra<Book>("book")
        binding.book = book

        btn_create.setOnClickListener {
            viewModel.insertBook()
            waitUntilFinishedToCloseActivity()
        }
        btn_update.setOnClickListener {
            viewModel.updateBook(binding.book!!)
            waitUntilFinishedToCloseActivity()
        }
        btn_delete.setOnClickListener {
            viewModel.deleteBook(binding.book!!)
            waitUntilFinishedToCloseActivity()
        }

    }

    private fun waitUntilFinishedToCloseActivity() {
        viewModel.isLoadingFinished.observe(this, { boolean ->
            if (boolean) {
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}