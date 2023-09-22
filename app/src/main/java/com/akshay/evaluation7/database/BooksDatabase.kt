package com.akshay.evaluation7.database

import com.akshay.evaluation7.model.BooksEntity
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BooksEntity::class], version = 1, exportSchema = false)
abstract class BooksDatabase:RoomDatabase() {
    abstract fun booksDao() : BooksDao

    companion object{

        @Volatile
        private var Instance : BooksDatabase? = null

        fun getDataBaseDetails(context: Context): BooksDatabase {
            return Instance ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context, BooksDatabase::class.java,"books_database"
                ).allowMainThreadQueries().build()
                Instance = instance
                instance
            }
        }

    }
}
