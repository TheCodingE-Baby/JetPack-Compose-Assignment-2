package com.example.jetpackcomposeassignment2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcomposeassignment2.roomDb.ToDoDao
import com.example.jetpackcomposeassignment2.roomDb.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1)
abstract class AppDb: RoomDatabase(){
    abstract fun toDoDao(): ToDoDao
}