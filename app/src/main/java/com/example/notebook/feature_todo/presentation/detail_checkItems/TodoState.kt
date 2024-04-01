package com.example.notebook.feature_todo.presentation.detail_checkItems

import com.example.notebook.feature_todo.domain.model.Todo

data class DetailTodoState(
    val isLoading: Boolean = false,
    val todo : Todo? = null,
)