package com.example.notebook.feature_todo.domain.use_cases



data class TodoUseCases(
    val getTodoUseCase: GetTodoUseCase,
    val deleTodoUseCase: DeleteTodoUseCase,
    val addTodoUseCase: AddTodoUseCase,
    val getTodoByIdUseCase: GetTodoByIdUseCase
)
