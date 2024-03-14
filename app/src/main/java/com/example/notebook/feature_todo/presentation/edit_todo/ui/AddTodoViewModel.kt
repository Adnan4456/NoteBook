package com.example.notebook.feature_todo.presentation.edit_todo.ui

import androidx.compose.runtime.mutableStateOf
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

    private val _description = mutableStateOf("")
    var description= _description

    private val _checkList = mutableListOf<ChecklistItem>()
    var checkList = _checkList


    fun setTitle(title:String){
        _title.value = title
    }

    fun setDescription(description:String){
        _description.value = description
    }

}