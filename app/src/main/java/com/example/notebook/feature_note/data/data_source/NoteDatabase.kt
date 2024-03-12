package com.example.notebook.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notebook.components.Converters
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_todo.data.data_source.TodoDao
import com.example.notebook.feature_todo.domain.model.Todo



@TypeConverters(Converters::class)
@Database(
    entities = [Note::class , Todo::class],
    version = 4,
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao:NoteDao
    abstract val todoDao: TodoDao

    companion object{
        const val  DATABASE_NAME = "notes_db"
    }
}