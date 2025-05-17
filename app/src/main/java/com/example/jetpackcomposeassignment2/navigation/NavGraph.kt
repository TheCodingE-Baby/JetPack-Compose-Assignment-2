package com.example.jetpackcomposeassignment2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx. hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
        composableLambda(NavRoutes.TodoList.route) {
            val viewModel: ToDoViewModel = hiltViewModel()
            TodoListScreen(
                viewModel = viewModel,
                onItemClick = { id ->
                    navController.navigate(NavRoutes.TodoDetail.createRoute(id))
                }
            )
        }

        // Detail Screen
        composableLambda(NavRoutes.TodoDetail.route) { backStackEntry ->
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