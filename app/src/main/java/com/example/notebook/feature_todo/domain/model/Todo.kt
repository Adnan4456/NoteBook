package com.example.notebook.feature_todo.domain.model

import androidx.room.*
import com.example.notebook.components.Converters


@Entity
data class Todo(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "date")
    val date: String?,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long?,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "checklist")
//    @Embedded
    val checklist: List<ChecklistItem>,

    val isBookMarked : Boolean = false,
    val isSecrete: Boolean = false,

)
