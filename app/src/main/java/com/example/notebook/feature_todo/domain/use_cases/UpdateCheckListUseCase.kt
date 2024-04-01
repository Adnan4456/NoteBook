package com.example.notebook.feature_todo.domain.use_cases

import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateCheckListUseCase
    @Inject
    constructor(
    private val repository: TodoRepository

) {

    suspend fun invoke(todoID: Int,  checklistItem: List<ChecklistItem>){
        repository.updatechecklistItem(todoID, checklistItem)
    }
}