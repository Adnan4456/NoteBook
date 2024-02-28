package com.example.notebook.feature_note.domain.use_cases

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNote: GetNoteUseCase
)