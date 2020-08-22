package com.quizsquiz.bookcollectionapp.network

import androidx.lifecycle.LiveData
import com.quizsquiz.bookcollectionapp.models.Book
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object Controller {
    var job: CompletableJob? = null
    private const val BASE_URL: String = "https://spring-boot-mysql-server-part0.herokuapp.com"
    fun getApiArguments() : BookApiService{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(BookApiService::class.java)
    }

    fun getBooksFromNetwork(): LiveData<List<Book>> {
        job = Job()
        return object : LiveData<List<Book>>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        val books = getApiArguments().getBooks()
                        withContext(Dispatchers.Main) {
                            value = books
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }
}