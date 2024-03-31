package com.example.notebook.feature_todo.data.repository

import com.example.notebook.feature_todo.data.data_source.TodoDao
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepositoryImpl
    @Inject constructor(
    private val dao: TodoDao
    ):TodoRepository
{
    override fun getTodo(): Flow<List<Todo>> {
        return dao.getTodo()
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return  withContext(Dispatchers.IO){
            dao.getTodoById(id)
        }
//        return dao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: Todo) {
         dao.insertTodo(todo)
    }

     override suspend fun deleteTodo(todo: Todo) {
       dao.deleteTodo(todo)
    }

    override  fun getBookMarkedTodo(): Flow<List<Todo>> {
        return dao.getBookMarkeTodo()
    }

    override  fun getSecretTodo(): Flow<List<Todo>> {
        return dao.getSecretTodo()
    }

//    override suspend fun updatechecklistItem(todo_id: Int, checklistItem: ChecklistItem) {
//        return dao.updateChecklistItem(todo_id , checklistItem)
//    }
}