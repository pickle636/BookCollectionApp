package com.quizsquiz.bookcollectionapp.module

import android.content.Context
import com.quizsquiz.bookcollectionapp.network.BookApiService
import com.quizsquiz.bookcollectionapp.repository.Repository
import com.quizsquiz.bookcollectionapp.room.BookDatabase
import com.quizsquiz.bookcollectionapp.room.Dao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun provideApiService(): BookApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://spring-boot-mysql-server-part0.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(BookApiService::class.java)
    }


    @Provides
    fun provideStudentDao(@ApplicationContext appContext: Context) : Dao {
        return BookDatabase.getInstance(appContext).bookDao
    }

    @ExperimentalCoroutinesApi
    @Provides
    fun provideBookDBRepository(apiService: BookApiService, dao: Dao) = Repository(apiService, dao)

}