package com.example.jetpackcomposeassignment2.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposeassignment2.data.ToDoModel
import com.example.jetpackcomposeassignment2.repo.ToDoRepository
import com.example.jetpackcomposeassignment2.retrofitsetup.api.RetrofitClient
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideToDoRepository(
        api: ToDoApi,
        dao: ToDoDao
    ): ToDoRepository {
        return ToDoRepository(api, db.toDoDao())
    }
}

@HiltViewModel
class ToDoViewModel(
    private val repo: ToDoRepository
): ViewModel() {

    sealed interface ToDoState{
        data object Loading: ToDoState
        data class Success(val todos: List<ToDoModel>): ToDoState
        data class Error(val msg: String): ToDoState
    }

    private val _state = mutableStateOf<ToDoState>(ToDoState.Loading)
    val state: State<ToDoState> get()= _state
    init {
        loadTodos()
    }

    fun loadTodos(){
        viewModelScope.launch {
            repo.getTodos()
                .collect { cachedTodos ->
                    _state.value = ToDoState.Success(cachedTodos)
                }

            try {
                repo.refreshTodos()
            } catch (e: Exception) {
                _state.value = ToDoState.Error("Couldn't refresh: ${e.message}")
            }
        }
    }
}