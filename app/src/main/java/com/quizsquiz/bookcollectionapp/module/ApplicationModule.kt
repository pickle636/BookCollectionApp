package com.quizsquiz.bookcollectionapp.module

import com.quizsquiz.bookcollectionapp.network.BookApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
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
    lateinit var database: MyDatabase

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyDatabase {
        // Make sure a read is made before writing so our onCreate callback is executed first
        database =  Room.databaseBuilder(
            context,
            MyDatabase::class.java, "database.db"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch {
                        MyDatabase.onCreate(database, context) // in companion of MyDatabase
                    }

                }
            })
            .build()
        return database
    }

    @Provides
    fun provideObjectDao(database: MyDatabase): ObjectDao {
        return database.objectDao()
    }
}