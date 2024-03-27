package com.example.notebook.feature_todo.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notebook.feature_todo.domain.model.ChecklistItem
import com.example.notebook.feature_todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Query("SELECT * from Todo where isSecrete = 0")
    fun getTodo(): Flow<List<Todo>>

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun getTodoById(id: Int):Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

//    @Query("Update Todo SET checklist = :checklistItem where id = :todoID")
//    suspend fun updateChecklistItem(todoID: Int,checklistItem: ChecklistItem)


    @Query("Select * from Todo WHERE isBookMarked = 1 and isSecrete = 0")
    fun getBookMarkeTodo(): Flow<List<Todo>>

    @Query("SELECT * from Todo WHERE isSecrete = 1")
    fun getSecretTodo(): Flow<List<Todo>>
}