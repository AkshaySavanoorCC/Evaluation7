package com.akshay.evaluation7.repository

import com.akshay.evaluation7.database.BooksDao
import com.akshay.evaluation7.model.BooksEntity
import kotlinx.coroutines.flow.Flow

class BooksRepository(private val booksDao: BooksDao) {
    val allBooks: Flow<List<BooksEntity>> = booksDao.getAllBooks()

    val allReadBooks :Flow<List<BooksEntity>> = booksDao.getAllReadBooks()



    fun insertNewBook(book: BooksEntity) {
        booksDao.addNewBook(book)
    }
    fun updateBookReadStatus(book: BooksEntity){
        booksDao.updateReadStatus(book)
    }

    fun deleteBook(book: BooksEntity){
        booksDao.deleteBook(book)
    }
}