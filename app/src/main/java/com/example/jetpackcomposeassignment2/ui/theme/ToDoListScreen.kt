package com.example.jetpackcomposeassignment2.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpackcomposeassignment2.data.ToDoModel
import com.example.jetpackcomposeassignment2.viewmodel.ToDoViewModel
import com.example.jetpackcomposeassignment2.viewmodel.ToDoViewModel.ToDoState

@Composable
fun TodoListScreen(
    viewModel: ToDoViewModel = viewModel(),
    onItemClick: (ToDoModel) -> Unit
) {
    val state by viewModel.state.collectAsState(initial = ToDoState.Loading)

    when (val currentState = state) {
        is ToDoState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ToDoState.Success -> {
            LazyColumn {
                items(currentState.todos) { todo ->
                    TodoItemCard(todo = todo, onItemClick = onItemClick)
                }
            }
        }
        is ToDoState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Error: ${currentState.msg}")
                Button(onClick = { viewModel.loadTodos() }) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
fun TodoItemCard(
    todo: ToDoModel,
    modifier: Modifier = Modifier,
    onItemClick: (ToDoModel) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { onItemClick(todo) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = todo.toDoName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "View details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}