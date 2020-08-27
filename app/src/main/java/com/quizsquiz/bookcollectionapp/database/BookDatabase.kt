package com.quizsquiz.bookcollectionapp.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quizsquiz.bookcollectionapp.models.Book

@Database(entities = [Book::class], version = 2)
abstract class BookDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao

    companion object {
        private var database: BookDatabase? = null
        fun getDatabase(application: Application): BookDatabase? {
            if (database == null) {
                database = Room.databaseBuilder(application.applicationContext, BookDatabase::class.java, "book_database").fallbackToDestructiveMigration().build()
            }
            return database
        }
    }
}