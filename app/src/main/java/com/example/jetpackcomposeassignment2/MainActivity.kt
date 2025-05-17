package com.example.jetpackcomposeassignment2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeassignment2.navigation.TodoNavHost
import com.example.jetpackcomposeassignment2.repo.ToDoRepository
import com.example.jetpackcomposeassignment2.ui.theme.JetpackComposeWithRetrofitAndRoomTheme
import com.example.jetpackcomposeassignment2.ui.theme.TodoListScreen
import com.example.jetpackcomposeassignment2.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ToDoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeWithRetrofitAndRoomTheme {
                val navController = rememberNavController()
                TodoNavHost(navController)
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    TodoListScreen(
                        viewModel,
                        onItemClick = TODO()
                    )
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ToDoAppPreview() {
    JetpackComposeWithRetrofitAndRoomTheme {
        Greeting("Android")
        TodoListScreen(
            viewModel = ToDoViewModel(ToDoRepository()),
            onItemClick = TODO()
        )
    }
}