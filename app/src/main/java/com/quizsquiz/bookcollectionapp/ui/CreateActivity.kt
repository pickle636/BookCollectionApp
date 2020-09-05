package com.quizsquiz.bookcollectionapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.databinding.ActivityCreateBinding
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.viewmodels.CreateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
@AndroidEntryPoint
class CreateActivity : BaseActivity() {

    private val viewModel : CreateViewModel by viewModels()
    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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