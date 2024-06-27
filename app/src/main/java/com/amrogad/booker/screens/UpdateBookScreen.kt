package com.amrogad.booker.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.amrogad.booker.room.BookEntity
import com.amrogad.booker.viewmodel.BookViewModel

@Composable
fun UpdateBookScreen(viewModel: BookViewModel, bookId: String?) {
    var inputText by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(value = inputText, onValueChange = { enteredText ->
            inputText = enteredText
        },
            label = { Text("Update Book") }, placeholder = { Text(text = "New Book Name") })
        Button(onClick = {
            var newBook = BookEntity(bookId!!.toInt(), inputText)
            viewModel.updateBook(newBook)
        }) {
           Text(text = "Update Book")
        }
    }
}