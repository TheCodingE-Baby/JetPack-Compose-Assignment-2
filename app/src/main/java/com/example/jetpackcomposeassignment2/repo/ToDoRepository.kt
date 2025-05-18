package com.example.jetpackcomposeassignment2.repo

import android.util.Log
import com.example.jetpackcomposeassignment2.data.ToDoModel
import com.example.jetpackcomposeassignment2.retrofitsetup.api.ToDoApi
import com.example.jetpackcomposeassignment2.roomDb.ToDoDao
import com.example.jetpackcomposeassignment2.roomDb.ToDoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToDoRepository(
    private val api: ToDoApi,
    private val dao: ToDoDao
) {
    suspend fun refreshTodos() {
           try {
               val todos = api.getToDos()
               dao.insertTodos(todos.map { it.toEntity() })
           } catch(e: Exception){
               Log.e("Repo", "Refresh failed.", e)
           }
    }

    fun ToDoModel.toEntity(): ToDoEntity{
        return ToDoEntity(
            toDoId = this.toDoId,
            toDoName = this.toDoName,
            isDone = this.isDone
        )
    }

    fun ToDoEntity.toDomain(): ToDoModel{
        return ToDoModel(
            toDoId = this.toDoId,
            userId = 0,
            toDoName = this.toDoName,
            isDone = this.isDone
        )
    }

    fun getTodos(): Flow<List<ToDoModel>> {
        return dao.getTodos().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}