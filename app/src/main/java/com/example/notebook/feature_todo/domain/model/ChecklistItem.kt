package com.example.notebook.feature_todo.domain.model

data class ChecklistItem(
    var uid: Long = 0L,
    var title: String = "",
    var isCompleted: Boolean = false
)
