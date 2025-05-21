package com.example.jetpackcomposeassignment2.repo

import android.util.Log
import com.example.jetpackcomposeassignment2.data.ToDoModel
import com.example.jetpackcomposeassignment2.retrofitsetup.api.ToDoApi
import com.example.jetpackcomposeassignment2.roomDb.ToDoDao
import com.example.jetpackcomposeassignment2.roomDb.ToDoEntity

class ToDoRepository(
    private val toDoApiService: ToDoApi, // Renamed for clarity
    private val toDoDao: ToDoDao         // Renamed for clarity
) {
    /**
     * Refreshes the local database with todos from the remote API.
     * @return Result<Unit> indicating success or failure of the operation.
     */
    suspend fun refreshTodos(): Result<Unit> {
        return try {
            // Ensure toDoApiService.getToDos() is executed on a background thread (e.g., Dispatchers.IO)
            val remoteTodos = toDoApiService.getToDos()
            // Ensure toDoDao.insertTodos() is executed on a background thread
            toDoDao.insertTodos(remoteTodos.map { it.toEntity() })
            Log.i("ToDoRepository", "Todos refreshed successfully.")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("ToDoRepository", "Failed to refresh todos from API.", e)
            Result.failure(e) // Propagate the exception for the caller to handle
        }
    }

    private fun ToDoModel.toEntity(): ToDoEntity {
        return ToDoEntity(
            toDoId = this.toDoId,
            toDoName = this.toDoName,
            userId = this.userId,
            isDone = this.isDone
        )
    }

    private fun ToDoEntity.toDomain(): ToDoModel {
        return ToDoModel(
            toDoId = this.toDoId,
            // Assuming userId in ToDoModel has a default or is handled elsewhere if not present in ToDoEntity
            // userId = 0, // If ToDoModel has a default (e.g. userId: Int = 0), this can be omitted.
            toDoName = this.toDoName,
            userId = this.userId,
            isDone = this.isDone
        )
    }
}