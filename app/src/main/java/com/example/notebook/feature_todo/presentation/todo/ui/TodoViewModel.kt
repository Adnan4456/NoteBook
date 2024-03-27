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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
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
    private lateinit var  allTodos: List<Todo>

     val _stateList = MutableStateFlow(TodoState())

    private var getTodoJob: Job? = null
    lateinit var checklist:ChecklistItem

    init {
        getAllTodos()
    }

    fun update() {
        allTodos =  todoState.value.todo
        Log.d("TAG","alltodo list = ${allTodos.size}")
    }
    @OptIn(FlowPreview::class)
    private fun getAllTodos() {

        getTodoJob?.cancel()
        getTodoJob =   todoUseCases.getTodoUseCase.invoke()
            .onEach {todos ->

                _todoState.value = todoState.value.copy(
                         todo = todos
                )
                _stateList .value = _stateList.value.copy(
                    todo = todos
                )

        }.launchIn(viewModelScope)

//        try{
//
//            viewModelScope.launch {
//                Log.d("TAG","inside viewmodel scope ")
//                val list = todoUseCases.getTodoUseCase
//                    .invoke()
//                    .                onEach {
//                    Log.d("TAG","size = ${it.size}")
//                }
//                    .onStart {
//                        Log.d("TAG","coroutine flow is started")
//                    }
//                    .flatMapConcat  {
//                    Log.d("TAG","in side flatMap function${it.size}")
//                        it.asFlow()
//                    }
//                    .onCompletion {
//                        Log.d("TAG","onCompleted function called")
//                    }
//                    .onEmpty {
//                        Log.d("TAG","onEmpty function called")
//                    }
//
////                    .toList()
////                Log.d("List" , "${list.size}")
////                _todoState.value = todoState.value.copy(
////                    todo = list
////                )
//
//                Log.d("TAG"," after function executed  ")
//            }
//
//        }catch (e: Exception) {
//            Log.d("exception","${e.message}")
//        }

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
                Log.d("checkitem = ","todo title = ${event.todo.title}    andchecklist title = ${event.checkItemList.title}")
            }
        }
    }

    fun onTaskDelete(task: Todo, item: ChecklistItem){
        _todoState.value
    }
}