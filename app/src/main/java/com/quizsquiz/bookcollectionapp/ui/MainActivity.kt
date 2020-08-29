package com.quizsquiz.bookcollectionapp.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
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
        Log.d(TAG, "onCreate")
    }

    private fun listItemClicked(book: Book) {
//        viewModel?.deleteBookByTouch(book)
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


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }


    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }


}

