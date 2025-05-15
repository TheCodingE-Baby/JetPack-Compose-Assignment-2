package com.example.jetpackcomposeassignment2.roomDb

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "todos")
data class ToDoEntity(
    @PrimaryKey val toDoId: Int,
    val toDoName: String,
    val isDone: Boolean
)

@Dao
interface ToDoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos: List<Unit>)

    @Query("SELECT * FROM todos")
    fun getTodos(): Flow<List<ToDoEntity>>
}
