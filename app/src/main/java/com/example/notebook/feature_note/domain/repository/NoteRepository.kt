package com.example.notebook.feature_note.domain.repository

import androidx.room.Query
import com.example.notebook.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow


interface NoteRepository {


    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun getBookMarkedNotes(): Flow<List<Note>>

    fun getSecreteNotes(): Flow<List<Note>>

}