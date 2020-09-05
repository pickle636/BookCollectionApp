package com.quizsquiz.bookcollectionapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.adapters.BookItemAdapter
import com.quizsquiz.bookcollectionapp.databinding.ActivityMainBinding
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel : MainViewModel by viewModels()
    var adapter: BookItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val b = fun(book: Book) {
            listItemClicked(book)
        }

        adapter = BookItemAdapter(b)
        binding.adapter = adapter
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.bookLiveData.observe(this, { bookList ->
            adapter!!.onChange(bookList)
        })

        checkConnectionInMainActivity(viewModel)

        fab_create.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }
    }

    private fun listItemClicked(book: Book) {
        if (viewModel.isConnected.get()){
            val intent = Intent(this, CreateActivity::class.java)
            intent.putExtra("book", book)
            startActivity(intent)
        }

    }


    @InternalCoroutinesApi
    override fun onResume() {
        super.onResume()
        if (viewModel.isConnected.get()) {
            viewModel.loadBooksFromServer()
        } else {
            viewModel.getAllBooks()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}

