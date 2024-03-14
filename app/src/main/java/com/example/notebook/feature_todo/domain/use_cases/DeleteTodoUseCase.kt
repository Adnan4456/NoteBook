package com.example.notebook.feature_todo.domain.use_cases

import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.repository.TodoRepository

class DeleteTodoUseCase(
    private val repository: TodoRepository
) {


    suspend operator fun  invoke(todo: Todo){
        repository.deleteTodo(todo)
    }
}