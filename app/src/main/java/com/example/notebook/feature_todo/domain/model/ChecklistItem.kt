package com.example.notebook.feature_todo.domain.model

data class ChecklistItem(
    val title: String,
    val isCompleted: Boolean = false
)
