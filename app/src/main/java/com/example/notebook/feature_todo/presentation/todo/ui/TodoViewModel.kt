package com.example.notebook.feature_todo.presentation.todo.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.use_cases.TodoUseCases
import com.example.notebook.feature_todo.domain.use_cases.UpdateCheckListUseCase
import com.example.notebook.feature_todo.presentation.todo.TodoEvents
import com.example.notebook.feature_todo.presentation.todo.TodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
@Inject constructor(
    private val todoUseCases: TodoUseCases,
    private val  updateCheckListUseCase:UpdateCheckListUseCase
    ):ViewModel() {


    private val _todoState =  mutableStateOf(TodoState())
    val todoState: State<TodoState> = _todoState
    private var getTodoJob: Job? = null
    lateinit var checklist:ChecklistItem

    init {
        getAllTodos()
    }

    private fun getAllTodos() {
        Log.d("TAG","inside Viewmodel")
        getTodoJob?.cancel()
        getTodoJob =   todoUseCases.getTodoUseCase.invoke()
            .onEach {todos ->
                Log.d("viewModel = ","${todos}")
                _todoState.value = todoState.value.copy(
                         todo = todos
                     )

        }.launchIn(viewModelScope)

    }

    fun onEvent(event: TodoEvents){

        when(event){
            is TodoEvents.DeleteTodo  -> {

            }
            is TodoEvents.RestoreTodo -> {

            }
            is TodoEvents.BookMarkTodo -> {

            }
            is TodoEvents.MakeSecretTodo -> {

            }
            is TodoEvents.EditTodo -> {
                Log.d("TAG","${event.todo.title}")
            }
            is TodoEvents.EditCheckItem -> {
//                _todoState.value.todo
                Log.d("checkitem = ","todo title = ${event.todo.title}    andchecklist title = ${event.checkItemList.title}")
            }
        }
    }

    fun onTaskDelete(task: Todo, item: ChecklistItem){
        _todoState.value
    }
}