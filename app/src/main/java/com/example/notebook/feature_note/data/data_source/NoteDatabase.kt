package com.example.notebook.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notebook.components.Converters
import com.example.notebook.feature_note.domain.model.Note


@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao:NoteDao

    companion object{
        const val  DATABASE_NAME = "notes_db"
    }
}