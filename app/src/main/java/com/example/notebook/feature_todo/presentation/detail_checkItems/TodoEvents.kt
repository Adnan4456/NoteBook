package com.example.notebook.feature_todo.presentation.detail_checkItems

import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.domain.model.Todo

sealed class TodoEvents {

    data class DeleteTodo (val todo:Todo): TodoEvents()

    object  RestoreTodo : TodoEvents()

    data class BookMarkTodo (val todo:Todo): TodoEvents()

    data class MakeSecretTodo (val todo:Todo): TodoEvents()

    data class EditTodo(val todo:Todo): TodoEvents()

    data class EditCheckItem (val todo:Todo , val checkItemList:ChecklistItem): TodoEvents()
}
