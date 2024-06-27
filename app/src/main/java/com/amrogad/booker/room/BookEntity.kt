package com.amrogad.booker.room
import androidx.room.Entity
import androidx.room.PrimaryKey

// an Entity is a table in the database, same as models
@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
)