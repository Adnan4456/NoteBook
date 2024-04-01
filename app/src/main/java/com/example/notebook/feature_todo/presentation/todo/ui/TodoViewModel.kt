package com.example.notebook.feature_todo.presentation.todo.ui

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
@Inject constructor(
    private val todoUseCases: TodoUseCases,
    ):ViewModel() {


    private val _todoState =  mutableStateOf(TodoState())
    val todoState: State<TodoState> = _todoState

    val _stateList = MutableStateFlow(TodoState())

    private var getTodoJob: Job? = null
    lateinit var checklist:ChecklistItem

    init {
        getAllTodos()
    }

    fun LongToTime(longValue: Long):String{
        // Calculate hours and minutes
        val hours = TimeUnit.SECONDS.toHours(longValue)
        val minutes = TimeUnit.SECONDS.toMinutes(longValue) % 60

        // Format the time
        return String.format("%02d:%02d", hours, minutes)
    }
    private fun getAllTodos() {

        getTodoJob?.cancel()
        getTodoJob =   todoUseCases.getTodoUseCase
            .invoke()
            .onEach {todos ->

                _todoState.value = todoState.value.copy(
                         todo = todos
                )
                _stateList .value = _stateList.value.copy(
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

            }
            is TodoEvents.EditCheckItem -> {
            }
        }
    }

    fun onTaskDelete(task: Todo){
        viewModelScope.launch(Dispatchers.IO) {

            todoUseCases.deleTodoUseCase.invoke(task)
        }

    }
}