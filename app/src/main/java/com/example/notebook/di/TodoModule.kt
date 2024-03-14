package com.example.notebook.di

import com.example.notebook.feature_note.data.data_source.NoteDatabase
import com.example.notebook.feature_todo.data.repository.TodoRepositoryImpl
import com.example.notebook.feature_todo.domain.repository.TodoRepository
import com.example.notebook.feature_todo.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun providesTodoRepository(db: NoteDatabase): TodoRepository {
        return  TodoRepositoryImpl(db.todoDao)
    }


    @Provides
    @Singleton
    fun providesTodoUseCase(repository: TodoRepository):TodoUseCases {
        return TodoUseCases(
            getTodoByIdUseCase = GetTodoByIdUseCase(repository),
            deleTodoUseCase = DeleteTodoUseCase(repository),
            addTodoUseCase = AddTodoUseCase(repository),
            getTodoUseCase = GetTodoUseCase(repository)
        )
    }
}