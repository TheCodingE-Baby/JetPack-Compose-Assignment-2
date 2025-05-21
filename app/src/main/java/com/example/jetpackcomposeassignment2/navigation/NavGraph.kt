package com.example.jetpackcomposeassignment2.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable//
import com.example.jetpackcomposeassignment2.viewmodel.ToDoViewModel // Assuming this is your ViewModel
import com.example.jetpackcomposeassignment2.viewmodel.TodoListScreen // Assuming this is your List Screen Composable
sealed class NavRoutes(val route: String) {
    data object TodoList : NavRoutes("todo_list")
    data object TodoDetail : NavRoutes("todo_detail/{name}") { // Changed {id} to {name}
        fun createRoute(name: String) = "todo_detail/$name" // Changed id: Int to name: String
    }
}
@Composable
fun TodoNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.TodoList.route
    ) {
        // List Screen
        composable(NavRoutes.TodoList.route) {
            val viewModel: ToDoViewModel = hiltViewModel() // Use hiltViewModel() directly
            TodoListScreen(
                viewModel = viewModel,
                onItemClick = { todo ->
                    navController.navigate(NavRoutes.TodoDetail.createRoute(todo.toDoName))
                }
            )
        }

        // Detail Screen
        composable(NavRoutes.TodoDetail.route) { backStackEntry ->
            val viewModel: ToDoViewModel = hiltViewModel()
            val todoId = backStackEntry.arguments?.getString("id")?.toIntOrNull()


            val todo = when (val state = viewModel.state) { // Assuming your ViewModel exposes a 'state' StateFlow
                is ToDoViewModel. -> state.todos.find { it.toDoId == todoId }
                else -> null // Handle loading/error states appropriately
            }
        }
    }
}