package com.example.jetpackcomposewithretrofitandroom.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposewithretrofitandroom.ui.theme.ToDoDetails
import com.example.jetpackcomposewithretrofitandroom.ui.theme.TodoListScreen

@Composable
fun TodoApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        @Composable("list") {
            TodoListScreen(viewModel(), navController)
        }
       @Composable("details/{todoId}") { backStackEntry ->
            ToDoDetails(backStackEntry.arguments?.getString("todoId")?.toInt() ?: 0)
        }
    }
}