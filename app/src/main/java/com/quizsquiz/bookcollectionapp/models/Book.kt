package com.quizsquiz.bookcollectionapp.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String?,
    var description: String?,
    var author: String?,
    var published: Int?
): Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        title = parcel.readString(),
        description = parcel.readString(),
        author = parcel.readString(),
        published = parcel.readInt()
    ) {
    }

    override fun describeContents(): Int = 0

    companion object : Parceler<Book> {

        override fun Book.write(p0: Parcel, p1: Int) {
            p0.writeInt(id)
            p0.writeString(title)
            p0.writeString(description)
            p0.writeString(author)
            p0.writeInt(published!!)
        }

        override fun create(parcel: Parcel): Book {
            return Book(parcel)
        }
    }
}