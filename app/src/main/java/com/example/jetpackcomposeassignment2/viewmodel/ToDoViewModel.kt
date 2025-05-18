package com.example.jetpackcomposeassignment2.ui.theme

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Data model (minimal, for clarity)
data class ToDoModel(
    val id: String,
    val toDoName: String,
    val isDone: Boolean
)

// ViewModel state
sealed class ToDoState {
    data object Loading : ToDoState()
    data class Success(val todos: List<ToDoModel>) : ToDoState()
    data class Error(val message: String) : ToDoState()
}

// Sample ViewModel (minimal, for testing)
class ToDoViewModel : ViewModel() {
    private val _state = MutableStateFlow<ToDoState>(ToDoState.Success(emptyList()))
    val state: StateFlow<ToDoState> = _state.asStateFlow()

    init {
        loadTodos()
    }

    fun loadTodos() {
        _state.value = ToDoState.Loading
        // Simulate data loading (replace with real data source)
        _state.value = ToDoState.Success(
            listOf(
                ToDoModel("1", "Buy groceries", false),
                ToDoModel("2", "Finish homework", true)
            )
        )
        // For error testing, uncomment:
        // _state.value = ToDoState.Error("Failed to load todos")
    }

    fun toggleTodoDone(todo: ToDoModel) {
        val currentState = _state.value
        if (currentState is ToDoState.Success) {
            val updatedTodos = currentState.todos.map {
                if (it.id == todo.id) it.copy(isDone = !it.isDone) else it
            }
            _state.value = ToDoState.Success(updatedTodos)
        }
    }
}

@Composable
fun TodoListScreen(
    viewModel: ToDoViewModel = viewModel(),
    onItemClick: (ToDoModel) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = state) {
            is ToDoState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ToDoState.Success -> {
                if (currentState.todos.isEmpty()) {
                    Text(
                        text = "No todos yet!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
                    ) {
                        items(currentState.todos, key = { it.id }) { todo ->
                            TodoItemCard(
                                todo = todo,
                                onItemClick = onItemClick,
                                onCheckedChange = { viewModel.toggleTodoDone(todo) },
                                modifier = Modifier.animateItem()
                            )
                        }
                    }
                }
            }
            is ToDoState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentState.message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadTodos() }) {
                        Text("Retry")
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
fun TodoItemCard(
    todo: ToDoModel,
    onItemClick: (ToDoModel) -> Unit,
    onCheckedChange: (ToDoModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onItemClick(todo) }
            .semantics { role = Role.Button },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = todo.isDone,
                onCheckedChange = { onCheckedChange(todo) },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.semantics { role = Role.Checkbox }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = todo.toDoName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
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