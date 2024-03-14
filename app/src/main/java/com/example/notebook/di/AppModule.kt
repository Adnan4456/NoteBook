package com.example.notebook.di

import android.app.Application
import androidx.room.Room
import com.example.notebook.feature_login.data.repository.DefaultEmailValidator
import com.example.notebook.feature_login.data.repository.DefaultPasswordValidator
import com.example.notebook.feature_login.data.repository.LoginRepositoryImpl
import com.example.notebook.feature_login.domain.repository.EmailValidator
import com.example.notebook.feature_login.domain.repository.LoginRepository
import com.example.notebook.feature_login.domain.repository.PasswordValidator
import com.example.notebook.feature_login.domain.use_case.EmailAndPasswordUseCase
import com.example.notebook.feature_login.domain.use_case.ValideteEmailUseCase
import com.example.notebook.feature_login.domain.use_case.ValidetePasswordUseCase
import com.example.notebook.feature_note.data.data_source.NoteDatabase
import com.example.notebook.feature_note.data.repository.NoteRepositoryImpl
import com.example.notebook.feature_note.domain.repository.NoteRepository
import com.example.notebook.feature_note.domain.use_cases.*
import com.example.notebook.feature_signup.data.repository.SignUpRepositoryImpl
import com.example.notebook.feature_signup.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
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
    fun providesFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesLoginRepository(firebaseAuth : FirebaseAuth): LoginRepository{
        return LoginRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesSignUpRespository(firebaseAuth : FirebaseAuth): SignUpRepository{
        return SignUpRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun providesNoteDatabase(application: Application): NoteDatabase{
        return  Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
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


    @Provides
    fun providesEmailValidator(): EmailValidator{
        return DefaultEmailValidator()
    }

    @Provides
    fun providesPasswordValidator():PasswordValidator{
        return DefaultPasswordValidator()
    }

    @Provides
    @Singleton
    fun providesEmailAndPasswordUseCase(emailUseCase: ValideteEmailUseCase ,passwordUseCase: ValidetePasswordUseCase)
    : EmailAndPasswordUseCase{
        return  EmailAndPasswordUseCase(
            emailUseCase = emailUseCase,
            passwordUseCase = passwordUseCase
        )
    }
}