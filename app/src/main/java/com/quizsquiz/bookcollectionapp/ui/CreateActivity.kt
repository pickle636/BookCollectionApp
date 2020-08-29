package com.quizsquiz.bookcollectionapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.quizsquiz.bookcollectionapp.R
import com.quizsquiz.bookcollectionapp.database.BookDatabase
import com.quizsquiz.bookcollectionapp.databinding.ActivityCreateBinding
import com.quizsquiz.bookcollectionapp.network.Controller
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.util.CreateViewModelFactory
import com.quizsquiz.bookcollectionapp.viewmodels.CreateViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CreateActivity : BaseActivity() {

    private val TAG = "CActivity"

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = BookDatabase.getDatabase(application)?.getBookDao()
        val viewModelFactory = CreateViewModelFactory(Repository(dao, Controller.getApiArguments()))
        val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(CreateViewModel::class.java) }

        val binding: ActivityCreateBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_create
        )
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel


        checkConnectionInCreateActivity(viewModel)

        Log.d(TAG, "onCreate")

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

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }
}