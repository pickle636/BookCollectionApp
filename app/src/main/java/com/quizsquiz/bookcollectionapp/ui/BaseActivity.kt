package com.quizsquiz.bookcollectionapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.quizsquiz.bookcollectionapp.viewmodels.CreateViewModel
import com.quizsquiz.bookcollectionapp.viewmodels.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

open class BaseActivity: AppCompatActivity() {


    override fun onResume() {
        super.onResume()

    }

    @ExperimentalCoroutinesApi
    fun checkConnectionInMainActivity(viewModel: MainViewModel) {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .build()
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.isConnected.set(true)
                Toast.makeText(baseContext, "Network is available", Toast.LENGTH_LONG).show()
            }

            override fun onLost(network: Network) {
                viewModel.isConnected.set(false)

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
    }

    @ExperimentalCoroutinesApi
    fun checkConnectionInCreateActivity(viewModel: CreateViewModel) {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .build()
        var callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                viewModel.isConnected.set(true)
                Toast.makeText(baseContext, "Network is available", Toast.LENGTH_LONG).show()
            }

            override fun onLost(network: Network) {
                viewModel.isConnected.set(false)

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
    }

}