package com.example.jetpackcomposeassignment2.repo

import com.example.jetpackcomposeassignment2.data.ToDoModel
import com.example.jetpackcomposeassignment2.database.AppDb
import com.example.jetpackcomposeassignment2.retrofitsetup.api.ToDoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TodoRepository(
    private val api: ToDoApi,
    private val db: AppDb
) {
    suspend fun fetchTodos(): List<ToDoModel> {
        return try {
            val todos = api.getToDos()
            db.toDoDao().insertTodos(todos.map { it.toEntity() })
            todos
        } catch (e: Exception) {
            db.toDoDao().getTodos().first().map { it.toTodo() }
        }
    }


    fun getTodosFlow(): Flow<List<ToDoModel>> {
        return db.toDoDao().getTodos().map { entities ->
            entities.map { it.toTodo() }
        }
    }
}