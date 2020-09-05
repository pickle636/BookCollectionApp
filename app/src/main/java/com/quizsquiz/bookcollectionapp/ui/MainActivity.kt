package com.quizsquiz.bookcollectionapp.ui

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.adapters.BookItemAdapter
import com.quizsquiz.bookcollectionapp.database.BookDatabase
import com.quizsquiz.bookcollectionapp.databinding.ActivityMainBinding
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.Controller
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.util.MainViewModelFactory
import com.quizsquiz.bookcollectionapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainActivity : BaseActivity() {
    val TAG = "MActivity"
    private var viewModel: MainViewModel? = null
    var adapter: BookItemAdapter? = null
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = BookDatabase.getDatabase(application)?.getBookDao()
        val repository = Repository(dao, Controller.getApiArguments())
        var viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        //class BookItemAdapter(private val clickListener:(Book) -> Unit) :

        var b = fun(book: Book) {
            listItemClicked(book)
        }

        adapter = BookItemAdapter(b)
        binding.adapter = adapter
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


        viewModel!!.bookLiveData.observe(this, { bookList ->
            adapter!!.onChange(bookList)
        })


        checkConnectionInMainActivity(viewModel!!)



        fab_create.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }


    }

    private fun listItemClicked(book: Book) {
        if (viewModel!!.isConnected.get()){
            val intent = Intent(this, CreateActivity::class.java)
            intent.putExtra("book", book)
            startActivity(intent)
        }

    }


    @InternalCoroutinesApi
    override fun onResume() {
        super.onResume()
        if (viewModel!!.isConnected.get()) {
            viewModel!!.loadBooksFromServer()
        } else {
            viewModel!!.getAllBooks()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}

