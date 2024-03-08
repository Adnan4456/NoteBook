package com.example.notebook.feature_note.data.repository

import com.example.notebook.feature_note.data.data_source.NoteDao
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl
    @Inject constructor(
    private val dao: NoteDao
): NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return  dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return  dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)

    }


    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override fun getBookMarkedNotes(): Flow<List<Note>> {
       return dao.getBookMarkedNotes()
    }

    override fun getSecreteNotes(): Flow<List<Note>> {
       return  dao.getSecreteNotes()
    }
}