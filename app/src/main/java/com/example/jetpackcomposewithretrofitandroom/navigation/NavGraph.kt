package com.example.jetpackcomposewithretrofitandroom.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx. hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.jetpackcomposewithretrofitandroom.ui.theme.ToDoDetails
import com.example.jetpackcomposewithretrofitandroom.ui.theme.TodoListScreen
import com.example.jetpackcomposewithretrofitandroom.viewmodel.ToDoViewModel

@Composable
fun NavGraph(){
    val navController = rememberNavController()

        NavHost(
        navController = navController,
        startDestination = "list"
    ){
        composable("list") {
            val viewModel = hiltViewModel<ToDoViewModel>()
            TodoListScreen(viewModel(), navController)
        }
        composable("details/{toDoId}") { backStackEntry ->
            val toDoId = backStackEntry.arguments?.getString("todoId")?.toInt() ?: 0
            ToDoDetails(toDoId)
        }
    }
}