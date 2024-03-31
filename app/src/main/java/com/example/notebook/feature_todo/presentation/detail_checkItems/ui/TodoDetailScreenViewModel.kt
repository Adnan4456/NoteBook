package com.example.notebook.feature_todo.presentation.detail_checkItems.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_todo.domain.use_cases.TodoUseCases
import com.example.notebook.feature_todo.presentation.detail_checkItems.DetailTodoState
import com.example.notebook.feature_todo.presentation.todo.TodoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailScreenViewModel
    @Inject
        constructor(
    private val todoUseCases: TodoUseCases,
    savedStateHandle: SavedStateHandle
):ViewModel() {


    private val _todoState =  mutableStateOf(DetailTodoState())
    val todoState: State<DetailTodoState> = _todoState

    init {

        savedStateHandle.get<Int>("todoId")?.let{todoId->
            if (todoId != -1){
                viewModelScope.launch {
                    todoUseCases.getTodoByIdUseCase(todoId)?.also{todo ->
                        _todoState.value = _todoState.value.copy(
                            todo = todo
                        )
                    }
                }
            }
        }
    }
}