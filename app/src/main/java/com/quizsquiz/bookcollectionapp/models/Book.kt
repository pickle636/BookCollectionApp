package com.quizsquiz.bookcollectionapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String?,
    var description: String?,
    var author: String?,
    var published: Int?
)