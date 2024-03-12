package com.example.notebook.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notebook.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("Select * from note where isSecrete = 0")
    fun getNotes(): Flow<List<Note>>

    @Query("Select * from note where id = :id")
    suspend fun getNoteById(id: Int) : Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("Select * from note where isBookMarked = 1 and isSecrete = 0")
    fun getBookMarkedNotes():Flow<List<Note>>
    @Query("Select * from note where isSecrete = 1")
    fun getSecreteNotes():Flow<List<Note>>
}