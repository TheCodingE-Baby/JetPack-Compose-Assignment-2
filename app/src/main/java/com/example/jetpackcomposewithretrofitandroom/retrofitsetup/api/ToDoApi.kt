package com.example.jetpackcomposewithretrofitandroom.retrofitsetup.api

import com.example.jetpackcomposewithretrofitandroom.data.ToDoModel
import retrofit2.http.GET

interface ToDoApi {
    @GET("todos")
    suspend fun getToDos(): List<ToDoModel>
}