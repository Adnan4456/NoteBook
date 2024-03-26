package com.example.notebook.feature_todo.domain.use_cases

import android.util.Log
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetTodoUseCase(
    private val repository: TodoRepository
) {

    operator fun invoke():Flow<List<Todo>>{
        return repository.getTodo()
//            .onEach {
//                Log.d("TAD","GETTodoUseCase ${it}")
//            }
    }
}