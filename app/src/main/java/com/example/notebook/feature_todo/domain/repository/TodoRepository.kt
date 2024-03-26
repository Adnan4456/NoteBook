package com.example.notebook.feature_todo.domain.repository

import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun getTodo(): Flow<List<Todo>>

    suspend fun getTodoById(id: Int): Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getBookMarkedTodo():Flow<List<Todo>>

     fun getSecretTodo():Flow<List<Todo>>

     suspend fun updatechecklistItem(todo_id: Int , checklistItem: ChecklistItem)

}