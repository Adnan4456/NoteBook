package com.example.notebook.feature_note.presentation.add_edit_note.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.notebook.feature_note.domain.model.Note
import com.example.notebook.feature_note.domain.use_cases.NoteUseCases
import com.example.notebook.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.nhaarman.mockitokotlin2.argumentCaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*



class AddEditNoteViewModelTest
{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteUseCases: NoteUseCases
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: AddEditNoteViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        noteUseCases = mock(NoteUseCases::class.java)
        savedStateHandle = mock(SavedStateHandle::class.java)

        Dispatchers.setMain(StandardTestDispatcher())

        viewModel = AddEditNoteViewModel(noteUseCases, savedStateHandle)
    }

    @Test
    fun `onEvent EnterTitle updates noteTitle state`() = runTest {
        val title = "Test Title"
        viewModel.onEvent(AddEditNoteEvent.EnterTitle(title))
        assertEquals(title, viewModel.noteTitle.value.text)
    }

    @Test
    fun `onEvent EnterContent updates noteContent state`() = runTest {
        val content = "Test Content"
        viewModel.onEvent(AddEditNoteEvent.EnterContent(content))
        assertEquals(content, viewModel.noteContent.value.text)
    }

    @Test
    fun `onEvent ChangeColor updates noteColor state`() = runTest {


        val color = 0xFF00FF
        viewModel.onEvent(AddEditNoteEvent.ChangeColor(color))
        assertEquals(color, viewModel.noteColor.value)
    }

    @Test
    fun `onEvent SaveNote saves the note`() = runTest {



        val title = "Test Title"
        val content = "Test Content"
        val color = 0xFF00FF



        viewModel.editorTitleState.setHtml(title)
        viewModel.editorContentState.setHtml(content)
        viewModel.onEvent(AddEditNoteEvent.ChangeColor(color))



        val note = Note(
            title = viewModel.editorTitleState.toHtml(),
            content = viewModel.editorContentState.toHtml(),
            id = 1,
            timestamp = System.currentTimeMillis(),
            color = viewModel.noteColor.value,
            isBookMarked = false,
            isSecrete =  false,
        )

        viewModel.onEvent(AddEditNoteEvent.SaveNote)

//        verify(noteUseCases).addNoteUseCase(note)

//        val noteCaptor = argumentCaptor<Note>()
 //
        assertEquals(title, viewModel.editorTitleState)
//        assertEquals(content, noteCaptor.firstValue.content)
//        assertEquals(color, noteCaptor.firstValue.color)
    }

    @Test
    fun `save note emits SaveNote event`() = runTest {
        viewModel.onEvent(AddEditNoteEvent.SaveNote)
        val event = viewModel.eventFlow.first()
        assert(event is AddEditNoteViewModel.UiEvent.SaveNote)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}