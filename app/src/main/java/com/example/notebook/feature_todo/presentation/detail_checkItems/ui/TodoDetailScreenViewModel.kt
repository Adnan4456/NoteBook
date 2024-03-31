package com.example.notebook.feature_todo.presentation.detail_checkItems.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.domain.model.Todo
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

    private var _title = mutableStateOf("")
    var title = _title

    private val _description = mutableStateOf("")
    var description= _description

    private val _todoState =  mutableStateOf(DetailTodoState())
    val todoState: State<DetailTodoState> = _todoState

    private val _currentFocusRequestId = mutableStateOf(-1L)
    val currentFocusRequestId: State<Long> = _currentFocusRequestId

     var _checkList = mutableListOf<ChecklistItem>()


    init {

        savedStateHandle.get<Int>("todoId")?.let{todoId->
            if (todoId != -1){
                viewModelScope.launch {
                    todoUseCases.getTodoByIdUseCase(todoId)?.also{todo ->
                        _todoState.value = _todoState.value.copy(
                            todo = todo
                        )
                    }

                    //now here assign values to states
                    _title.value = _todoState.value.todo!!.title
                    _description.value = _todoState.value.todo!!.description
                    _checkList = _todoState.value.todo!!.checklist as MutableList<ChecklistItem>
                }
            }
        }
    }
    fun onFocusGot(item: ChecklistItem) {
        _currentFocusRequestId.value = item.uid
    }
    fun onCheckableDelete(item: ChecklistItem) {
        _checkList.remove(item)
    }

    fun onCheckableValueChange(item: ChecklistItem, value: String) {
        val index = _checkList.indexOfFirst {
            item.uid == it.uid
        }
        _checkList[index] = item.copy(title = value)
    }

    fun onCheckableCheck(item: ChecklistItem, checked: Boolean) {
        val index = _checkList.indexOfFirst {
            item.uid == it.uid
        }
        _checkList[index] = item.copy(isCompleted = checked)
    }

}