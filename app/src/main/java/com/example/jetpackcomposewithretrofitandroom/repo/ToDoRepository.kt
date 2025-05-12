package com.example.jetpackcomposewithretrofitandroom.repo

import com.example.jetpackcomposewithretrofitandroom.data.ToDoModel
import com.example.jetpackcomposewithretrofitandroom.database.appDb
import com.example.jetpackcomposewithretrofitandroom.retrofitsetup.api.ToDoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TodoRepository(
    private val api: ToDoApi,
    private val db: appDb
) {
    suspend fun fetchTodos(): List<ToDoModel> {
        return try {
            val todos = api.getToDos()
            db.toDoDao().insertTodos(todos.map { it.toEntity() })
            todos
        } catch (e: Exception) {
            db.todoDao().getTodos().first().map { it.toTodo() }
        }
    }

    fun getTodosFlow(): Flow<List<Todo>> {
        return db.todoDao().getTodos().map { entities ->
            entities.map { it.toTodo() }
        }
    }
}