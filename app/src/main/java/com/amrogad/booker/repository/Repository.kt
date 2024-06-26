package com.amrogad.booker.repository

import com.amrogad.booker.room.BookEntity
import com.amrogad.booker.room.BooksDB

class Repository (val booksDB: BooksDB) {
    suspend fun addBookToRoom(bookEntity: BookEntity){
        booksDB.bookDao().addBook(bookEntity)
    }
    fun getAllBooks() = booksDB.bookDao().getAllBooks()

    suspend fun deleteBook(bookEntity: BookEntity){
        booksDB.bookDao().deleteBook(bookEntity)
    }
}