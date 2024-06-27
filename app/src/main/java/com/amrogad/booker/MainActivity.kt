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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amrogad.booker.repository.Repository
import com.amrogad.booker.room.BookEntity
import com.amrogad.booker.room.BooksDB
import com.amrogad.booker.ui.theme.BookerTheme
import com.amrogad.booker.viewmodel.BookViewModel
import com.amrogad.booker.screens.UpdateBookScreen


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
                    // Navigation
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen"){
                        composable("MainScreen"){
                            MainScreen(viewModel, navController)
                        }
                        composable("UpdateBookScreen/{bookId}"){
                            UpdateBookScreen(viewModel, bookId = it.arguments?.getString("bookId"))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: BookViewModel, navController: NavHostController) {
    var inputText by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 20.dp, start = 5.dp, end = 5.dp)
    ) {
        OutlinedTextField(value = inputText, onValueChange = {
            enteredText -> inputText = enteredText
        }, placeholder = {
            Text(
                text = "Insert book name and page"
            )
        })
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            viewModel.addBook(BookEntity(0, inputText))
        }, colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
            Text(text = "Add Book")
        }
        BooksList(viewModel, navController)
    }
}

@Composable
fun BookCard(viewModel: BookViewModel, book: BookEntity, navController: NavHostController){
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = book.title, fontSize = 20.sp, modifier = Modifier.padding(start = 4.dp, end = 4.dp))
            IconButton(onClick = {viewModel.deleteBook(book)}) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
            IconButton(onClick = {
                navController.navigate("UpdateBookScreen/${book.id}")
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    }
}

@Composable
fun BooksList(viewModel: BookViewModel, navController: NavHostController){
    val books by viewModel.books.collectAsState(initial = emptyList())
    LazyColumn {
        items(items = books){
            book -> BookCard(viewModel, book, navController)
        }
    }
}