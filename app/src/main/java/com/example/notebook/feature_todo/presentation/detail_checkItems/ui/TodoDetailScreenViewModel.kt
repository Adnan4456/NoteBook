package com.example.notebook.feature_todo.presentation.detail_checkItems.ui

import android.util.Log
import android.widget.Toast
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
import com.example.notebook.feature_todo.domain.use_cases.UpdateCheckListUseCase
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
    private val updateTodo: UpdateCheckListUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {


    private val _Id = mutableStateOf(-1)

    private val _taskCompleted = mutableStateOf(false)
    var taskCompleted = _taskCompleted

    private var _title = mutableStateOf("")
    var title = _title

    private val _description = mutableStateOf("")
    var description= _description

    private val _todoState =  mutableStateOf(DetailTodoState())
    val todoState: State<DetailTodoState> = _todoState

    private val _currentFocusRequestId = mutableStateOf(-1L)
    val currentFocusRequestId: State<Long> = _currentFocusRequestId


    private var _time = mutableStateOf("")
    var time = _time

    private var _date = mutableStateOf("")
    var date = _date


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
                    _Id.value = _todoState.value.todo!!.id
                    _title.value = _todoState.value.todo!!.title
                    _description.value = _todoState.value.todo!!.description
                    _checkList = _todoState.value.todo!!.checklist as MutableList<ChecklistItem>
                    _date.value = _todoState.value.todo!!.date!!
                    _time.value = _todoState.value.todo!!.timestamp!!.toString()
                    _taskCompleted.value = todoState.value.todo!!.completed
                    Log.d("TAG", "Task  = ${_taskCompleted.value}")
                }
            }
        }
    }
    fun onFocusGot(item: ChecklistItem) {
        _currentFocusRequestId.value = item.uid
    }
    fun onCheckableDelete(item: ChecklistItem) {

        _checkList.remove(item)
        Log.d("TAG","list size = ${_checkList.size}")
        updateTodo()
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
        Log.d("TAG","${item.title} = ${checked}")
        _checkList[index] = item.copy(isCompleted = checked)

        updateTodo()
    }

    fun onTaskDelete(task: Todo){
        viewModelScope.launch(Dispatchers.IO) {

            todoUseCases.deleTodoUseCase.invoke(task)
        }

    }

    private fun updateTodo(){

        _taskCompleted.value = _checkList.any { !it.isCompleted }
        Log.d("TAG","${ _taskCompleted.value}")
        viewModelScope.launch (Dispatchers.IO){

            if(taskCompleted.value){
                Log.d("TAG","Task is completed .Great ")

                todoUseCases.addTodoUseCase.invoke(
                    Todo(
                        id = _Id.value,
                        title = _title.value,
                        description = _description.value,
                        date = _date.value,
                        timestamp = _time.value.toLong(),
                        completed = _taskCompleted.value,
                        isBookMarked = false,
                        isSecrete =  false,
                        checklist = _checkList
                    )
                )
            }
            else
            {
                updateTodo.invoke(
                    todoID = _todoState.value.todo!!.id,
                    checklistItem = _checkList
                )
            }


//            todoUseCases.addTodoUseCase.invoke(
//                Todo(
//                    title = _title.value,
//                    description = _description.value,
//                    checklist = _checkList,
//                    date = _date.value,
//                    timestamp = _time.value.toLong(),
//                    isBookMarked = false,
//                    isSecrete =  false
//                )
//            )
        }
    }
}