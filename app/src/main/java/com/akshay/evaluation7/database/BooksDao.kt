package com.akshay.evaluation7.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.akshay.evaluation7.model.BooksEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Insert
    fun addNewBook(book : BooksEntity)

    @Query("SELECT * FROM books_collection")
    fun getAllBooks(): Flow<List<BooksEntity>>

    @Delete
    fun deleteBook(book: BooksEntity)

    @Update
    fun updateReadStatus(book: BooksEntity)


    @Query("SELECT * FROM books_collection WHERE read_status=1")
    fun getAllReadBooks(): Flow<List<BooksEntity>>

}