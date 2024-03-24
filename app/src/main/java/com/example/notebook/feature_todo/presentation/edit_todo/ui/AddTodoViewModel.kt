package com.example.notebook.feature_todo.presentation.edit_todo.ui

import android.widget.Checkable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel
    @Inject
    constructor
    ():ViewModel() {

    private var _title = mutableStateOf("")
    var title = _title

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

    private val _checkList = mutableListOf<ChecklistItem>()
    var checkList = _checkList

    private val _checkables = mutableStateListOf<Checkable>()
    val checkables: SnapshotStateList<Checkable> = _checkables

    fun setTitle(title:String){
        _title.value = title
    }

    fun setDescription(description:String){
        _description.value = description
    }

}