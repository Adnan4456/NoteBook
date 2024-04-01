package com.example.notebook.feature_todo.presentation.edit_todo.ui

import android.util.Log
import android.widget.Checkable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.use_cases.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel
    @Inject
    constructor(
        private val todoUseCases: TodoUseCases
        )
    :ViewModel() {

    var totalTimeInMillis  = mutableStateOf(-1L)

    private val _result = mutableStateOf(-1L)
    var result = _result

    private var _title = mutableStateOf("")
    var title = _title

    private val _currentFocusRequestId = mutableStateOf(-1L)
    val currentFocusRequestId: State<Long> = _currentFocusRequestId

    private var _showDatePickerDialog = mutableStateOf(false)
     var showDatePickerDialog = _showDatePickerDialog

    private var _date = mutableStateOf("")
    var date = _date

    private var _showTimePickerDialog = mutableStateOf(false)
    var showTimePickerDialog = _showTimePickerDialog

    private var _time = mutableStateOf("")
    var time = _time

    private var _alarmSet = mutableStateOf(false)
    var alarmSet = _alarmSet

    private val _description = mutableStateOf("")
    var description= _description

    private val _checkList = mutableStateListOf<ChecklistItem>()
    var checkList:SnapshotStateList<ChecklistItem> = _checkList

    private val newId: Long
        get(){
            return System.currentTimeMillis()
        }
    fun setTitle(title:String){
        _title.value = title
    }

    fun setDescription(description:String){
        _description.value = description
    }

    fun onFocusGot(item: ChecklistItem) {
        _currentFocusRequestId.value = item.uid
    }

    fun onCheckableDelete(item: ChecklistItem) {
        checkList.remove(item)
    }

    fun onCheckableValueChange(item: ChecklistItem, value: String) {
        val index = checkList.indexOfFirst {
            item.uid == it.uid
        }
        checkList[index] = item.copy(title = value)
    }

    fun onCheckableCheck(item: ChecklistItem, checked: Boolean) {
        val index = checkList.indexOfFirst {
            item.uid == it.uid
        }

        checkList[index] = item.copy(isCompleted = checked)
    }
    fun onAddCheckable(item: ChecklistItem? =null){
        val id = newId
        val checkable = ChecklistItem(
            uid = id,
        )
        _currentFocusRequestId.value = id

        if(item==null){
            checkList.add(checkable)
            return
        }
        val index = checkList.indexOfFirst {
            item.uid == it.uid
        }
        if(index > -1){
            checkList.add(index+1,checkable)
        }
        else{
            checkList.add(checkable)
        }
    }

    fun onSave(){

        viewModelScope.launch {
            //make TodoClass object and pass into repository
            val result = todoUseCases.addTodoUseCase.invoke(
                Todo(
                    title = title.value,
                    description =  description.value,
                    date = _date.value,
                    timestamp = totalTimeInMillis.value,
                    checklist = checkList,
                    isSecrete = false,
                    isBookMarked = false,
                )
            )
            if(result >0){
                _result.value = result
            }
        }
    }

    fun resetAllVariable(){
        _result.value = -1
        _description.value = ""
        _title.value = ""
        _date.value = ""
        _time.value = ""
        _checkList.clear()
        _currentFocusRequestId.value = -1
        _alarmSet.value = false
    }
}