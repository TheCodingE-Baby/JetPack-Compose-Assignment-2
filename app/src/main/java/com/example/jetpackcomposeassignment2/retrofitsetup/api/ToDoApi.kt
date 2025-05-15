package com.example.jetpackcomposeassignment2.retrofitsetup.api

import com.example.jetpackcomposeassignment2.data.ToDoModel
import retrofit2.http.GET

interface ToDoApi {
    @GET("todos")
    suspend fun getToDos(): List<ToDoModel>
}