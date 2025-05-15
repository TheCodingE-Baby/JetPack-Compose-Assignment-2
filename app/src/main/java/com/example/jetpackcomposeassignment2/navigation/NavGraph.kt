package com.example.jetpackcomposeassignment2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx. hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposeassignment2.ui.theme.ToDoDetails
import com.example.jetpackcomposeassignment2.ui.theme.TodoListScreen
import com.example.jetpackcomposeassignment2.viewmodel.ToDoViewModel

@Composable
fun NavGraph(){
    val navController = rememberNavController()

        NavHost(
        navController = navController,
        startDestination = "list"
    ){
        composableLambda("list") run {
            val viewModel = hiltViewModel<ToDoViewModel>()
            TodoListScreen(viewModel(), navController)
        }
        composableLambda("details/{toDoId}") run  { backStackEntry ->
        val toDoId = backStackEntry.arguments?.getString("todoId")?.toInt() ?: 0
        ToDoDetails(toDoId)
        }
    }
}