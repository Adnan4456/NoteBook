package com.example.notebook.feature_todo.presentation.todo

import com.example.notebook.feature_todo.domain.model.Todo

sealed class TodoEvents {

    data class DeleteTodo (val todo:Todo): TodoEvents()

    object  RestoreTodo : TodoEvents()

    data class BookMarkTodo (val todo:Todo): TodoEvents()

    data class MakeSecretTodo (val todo:Todo): TodoEvents()
}
