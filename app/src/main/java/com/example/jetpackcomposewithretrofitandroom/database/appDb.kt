package com.example.jetpackcomposewithretrofitandroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackcomposewithretrofitandroom.roomDb.ToDoDao
import com.example.jetpackcomposewithretrofitandroom.roomDb.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1)
abstract class appDb: RoomDatabase(){
    abstract fun toDoDao(): ToDoDao
}