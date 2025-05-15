package com.example.jetpackcomposewithretrofitandroom.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import com.example.jetpackcomposewithretrofitandroom.data.ToDoModel
import com.example.jetpackcomposewithretrofitandroom.viewmodel.ToDoViewModel

@Composable
fun TodoListScreen(viewModel: ToDoViewModel, navController: NavController) {
    val todos by viewModel.todos

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(todos) { todo ->
            TodoItem(
                todo = todo,
                onClick = { navController.navigate("details/${todo.toDoId}") }
            )
        }
    }
}

@Composable
fun TodoItem(todo: ToDoModel, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (todo.isDone)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = todo.toDoName,
            modifier = Modifier.padding(16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}