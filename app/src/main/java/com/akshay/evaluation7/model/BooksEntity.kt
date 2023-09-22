package com.akshay.evaluation7.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_collection")
class BooksEntity(
    @PrimaryKey(autoGenerate = true)
    var bookId:Long,
    @ColumnInfo(name = "author_name")
    val author:String,

    @ColumnInfo(name = "book_title")
    val title:String,

    @ColumnInfo(name = "read_status")
    var isRead:Boolean
)
{




}