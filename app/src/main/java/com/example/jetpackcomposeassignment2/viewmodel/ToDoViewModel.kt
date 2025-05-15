package com.example.jetpackcomposeassignment2.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeassignment2.data.ToDoModel
import com.example.jetpackcomposeassignment2.repo.TodoRepository
import kotlinx.coroutines.launch

class ToDoViewModel (repository: TodoRepository): ViewModel(){
    private val _todos = mutableStateOf<List<ToDoModel>>(emptyList())
    val todos : State<List<ToDoModel>> = _todos

    init {
        viewModelScope.launch {

            refreshTodos(repository)
            repository.getTodosFlow().collect { cachedTodos ->
                _todos.value = cachedTodos
            }
            refreshTodos(repository)
        }
    }

    private suspend fun refreshTodos(repository: TodoRepository) {
        _todos.value = repository.fetchTodos()
    }
}
