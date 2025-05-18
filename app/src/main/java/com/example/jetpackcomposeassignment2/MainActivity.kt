package com.example.jetpackcomposeassignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeassignment2.navigation.TodoNavHost
import com.example.jetpackcomposeassignment2.ui.theme.JetpackComposeWithRetrofitAndRoomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeWithRetrofitAndRoomTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = { Text("To-Do App") }
            )
        }
    ) { innerPadding ->
        // Navigation host for the app
        TodoNavHost(
            navController = navController
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoAppPreview() {
    JetpackComposeWithRetrofitAndRoomTheme {
        MainScreen()
    }
}