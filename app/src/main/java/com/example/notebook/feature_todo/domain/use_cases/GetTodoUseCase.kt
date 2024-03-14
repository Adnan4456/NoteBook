package com.example.notebook.feature_todo.domain.use_cases

import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodoUseCase(
    private val repository: TodoRepository
) {


    operator fun invoke():Flow<List<Todo>>{
        return repository.getTodo()
    }
}