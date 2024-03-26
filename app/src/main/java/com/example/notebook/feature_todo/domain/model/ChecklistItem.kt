package com.example.notebook.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ChecklistItem(
     var uid: Long = 0L,
    var title: String = "",
    var isCompleted: Boolean = false
)
