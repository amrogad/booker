package com.amrogad.booker.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// DAO is used to add methods so we can interact with the database (add, delete, update and get all)
@Dao
interface BookDAO {
    @Insert
    suspend fun addBook(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Delete
    suspend fun deleteBook(bookEntity: BookEntity)

    @Update
    suspend fun updateBook(bookEntity: BookEntity)
}