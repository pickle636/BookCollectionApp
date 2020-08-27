package com.quizsquiz.bookcollectionapp.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.adapters.BookItemAdapter
import com.quizsquiz.bookcollectionapp.database.BookDatabase
import com.quizsquiz.bookcollectionapp.databinding.ActivityMainBinding
import com.quizsquiz.bookcollectionapp.models.Book
import com.quizsquiz.bookcollectionapp.network.Controller
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.util.MainViewModelFactory
import com.quizsquiz.bookcollectionapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {
    private var viewModel: MainViewModel? = null
    var adapter: BookItemAdapter? = null
    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
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

        if (checkConnection()) {
            viewModel!!.loadBooksFromServer()
        } else {
            viewModel!!.getAllBooks()
        }

        viewModel!!.bookLiveData.observe(this, { bookList ->
            adapter!!.onChange(bookList)
        } )



        fab_create.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

    }

    private fun listItemClicked(book: Book) {
//        viewModel?.deleteBookByTouch(book)
    }




    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    private fun checkConnection(): Boolean {
        var connectivity = false
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .build()
        var callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                connectivity = true
                viewModel!!.loadBooksFromServer()
                Toast.makeText(baseContext, "Network is available", Toast.LENGTH_LONG).show()
            }

            override fun onLost(network: Network) {
                connectivity = false

                Toast.makeText(baseContext, "Network was lost", Toast.LENGTH_LONG).show()
            }
        }
        try {
            manager.unregisterNetworkCallback(callback)
        } catch (e: Exception) {
            Log.w(
                "MainActivity",
                "NetworkCallback for Wi-fi was not registered or already unregistered"
            )
        }

        manager.registerNetworkCallback(networkRequest, callback)
        return connectivity
    }




}

