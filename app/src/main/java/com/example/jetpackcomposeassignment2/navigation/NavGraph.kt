package com.example.jetpackcomposeassignment2.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackcomposeassignment2.ui.theme.ToDoDetails
import com.example.jetpackcomposeassignment2.ui.theme.TodoListScreen
import com.example.jetpackcomposeassignment2.viewmodel.ToDoViewModel

sealed class NavRoutes(val route: String) {
    object TodoList : NavRoutes("todo_list")
    object TodoDetail : NavRoutes("todo_detail/{id}") {
        fun createRoute(id: Int) = "todo_detail/$id"
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
            val viewModel: ToDoViewModel = hiltViewModel()
            TodoListScreen(
                viewModel = viewModel,
                onItemClick = { todo ->
                    navController.navigate(NavRoutes.TodoDetail.createRoute(todo.id))
                }
            )
        }

        // Detail Screen
        composable(NavRoutes.TodoDetail.route) { backStackEntry ->
            val viewModel: ToDoViewModel = hiltViewModel()
            val todoId = backStackEntry.arguments?.getString("id")?.toIntOrNull()

            ToDoDetails(
                viewModel = viewModel,
                todoId = todoId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}