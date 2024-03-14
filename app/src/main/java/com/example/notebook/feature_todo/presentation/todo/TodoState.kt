package com.example.notebook.feature_todo.presentation.todo

import com.example.notebook.feature_todo.domain.model.Todo

data class TodoState(
    val isLoading: Boolean = false,
    val todo : List<Todo> = emptyList(),
)