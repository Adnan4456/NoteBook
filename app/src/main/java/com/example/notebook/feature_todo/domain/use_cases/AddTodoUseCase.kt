package com.example.notebook.feature_todo.domain.use_cases

import android.util.Log
import com.example.notebook.common.InvalideTodoException
import com.example.notebook.feature_todo.domain.model.Todo
import com.example.notebook.feature_todo.domain.repository.TodoRepository

class AddTodoUseCase(
    private val repository: TodoRepository
) {
    @Throws(InvalideTodoException::class)
    suspend operator fun invoke(todo: Todo):Long {

        if (todo.title.isBlank()){

            throw InvalideTodoException("title cannot be blank")
        }
        if (todo.description.isBlank()){
            throw InvalideTodoException("decription cannot be blank")
        }
        if (todo.checklist.isEmpty()){
            throw InvalideTodoException("Checklist cannot be blank")
        }
        val result = repository.insertTodo(todo)
        Log.d("TAG","insert result  = ${result}")
       return  result
    }
}