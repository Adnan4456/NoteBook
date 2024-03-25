package com.example.notebook.feature_todo.domain.model

data class ChecklistItem(
    var uid: Long = 0L,
    val title: String = "",
    val isCompleted: Boolean = false
)
