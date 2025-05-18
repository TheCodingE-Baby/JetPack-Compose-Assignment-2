package com.example.jetpackcomposeassignment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpackcomposeassignment2.ui.theme.ToDoDetails
import com.example.jetpackcomposeassignment2.ui.theme.ToDoViewModel
import com.example.jetpackcomposeassignment2.ui.theme.TodoListScreen
import androidx.hilt.navigation.compose.hiltViewModel as composeHiltViewModel

sealed class NavRoutes(val route: String) {
    data object TodoList : NavRoutes("todo_list")
    data object TodoDetail : NavRoutes("todo_detail/{id}") {
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
            val viewModel: ToDoViewModel = composeHiltViewModel().also {
                TodoListScreen(
                    viewModel = it,
                    onItemClick = { todo ->
                        navController.navigate(NavRoutes.TodoDetail.createRoute(todo.toDoId))
                    }
                )
            }
        }

        // Detail Screen
        composable(NavRoutes.TodoDetail.route) { backStackEntry ->
            val viewModel: ToDoViewModel = composeHiltViewModel()
            val todoId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            val todo = viewModel.state.value.let { state ->
                when (state) {
                    is ToDoViewModel.ToDoState.Success -> state.todos.find { it.toDoId == todoId }
                    else -> null
                }
            }
            todo?.let {
                ToDoDetails(todo = it)

            }
        }
    }
}