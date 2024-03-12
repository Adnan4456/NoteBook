package com.example.notebook.feature_todo.domain.use_cases

import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.repository.TodoRepository

class GetTodoByIdUseCase(
    private val repository: TodoRepository
) {

    suspend operator fun invoke(id: Int): Todo?{
        return repository.getTodoById(id)
    }
}