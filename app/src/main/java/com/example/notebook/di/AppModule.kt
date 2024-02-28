package com.example.notebook.di

import android.app.Application
import androidx.room.Room
import com.example.notebook.feature_note.data.data_source.NoteDatabase
import com.example.notebook.feature_note.data.repository.NoteRepositoryImpl
import com.example.notebook.feature_note.domain.repository.NoteRepository
import com.example.notebook.feature_note.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNoteDatabase(application: Application): NoteDatabase{
        return  Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun providesNoteRepository(db: NoteDatabase) : NoteRepository {
        return  NoteRepositoryImpl(db.noteDao)
    }


    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCases{
        return  NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase( repository),
            getNote = GetNoteUseCase(repository)
        )
    }
}