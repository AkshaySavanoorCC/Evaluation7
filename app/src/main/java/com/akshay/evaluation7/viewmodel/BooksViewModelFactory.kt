package com.akshay.evaluation7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akshay.evaluation7.repository.BooksRepository

class BooksViewModelFactory(private val repository: BooksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksViewModel::class.java)){
            return BooksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }
}