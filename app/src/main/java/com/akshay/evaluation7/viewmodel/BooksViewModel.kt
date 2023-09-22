package com.akshay.evaluation7.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import com.akshay.evaluation7.model.BooksEntity
import com.akshay.evaluation7.repository.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BooksViewModel(private val repository: BooksRepository) : ViewModel() {

    val allBooksLiveData : LiveData<List<BooksEntity>> = repository.allBooks.asLiveData()
    val allReadBooksLiveData : LiveData<List<BooksEntity>> = repository.allReadBooks.asLiveData()

    fun insertNewBook(books: BooksEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertNewBook(books)
        }
    }

    fun deleteBook(books: BooksEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteBook(books)
        }
    }

    fun updateToggleStatus(updatedBook: BooksEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBookReadStatus(updatedBook)
        }
    }





}