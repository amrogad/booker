package com.amrogad.booker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amrogad.booker.repository.Repository
import com.amrogad.booker.room.BookEntity
import com.amrogad.booker.room.BooksDB
import com.amrogad.booker.ui.theme.BookerTheme
import com.amrogad.booker.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    val db = BooksDB.getInstance(context)
                    val repository = Repository(db)
                    val viewModel = BookViewModel(repository = repository)
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: BookViewModel) {
    var inputText by remember { mutableStateOf("") }
    Column(
        Modifier.padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(value = inputText, onValueChange = {
            enteredText -> inputText = enteredText
        }, placeholder = {
            Text(
                text = "Book name"
            )
        })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            viewModel.addBook(BookEntity(0, inputText))
        }) {
            Text(text = "Add book")
        }
        BooksList(viewModel)
    }
}

@Composable
fun BookCard(viewModel: BookViewModel, book: BookEntity){
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Row {
            Text(text = book.title, fontSize = 24.sp, modifier = Modifier.padding(start = 4.dp, end = 4.dp))
        }
    }
}

@Composable
fun BooksList(viewModel: BookViewModel){
    val books by viewModel.books.collectAsState(initial = emptyList())
    LazyColumn {
        items(items = books){
            book -> BookCard(viewModel, book)
        }
    }
}