package com.amrogad.booker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amrogad.booker.repository.Repository
import com.amrogad.booker.room.BookEntity
import kotlinx.coroutines.launch

class BookViewModel(val repository: Repository) : ViewModel() {
    fun addBook(book: BookEntity) {
        viewModelScope.launch {
            repository.addBookToRoom(book)
        }
    }
    val books = repository.getAllBooks()
}