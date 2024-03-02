package com.example.notebook.feature_login.domain.use_case

data class EmailAndPasswordUseCase(
    val emailUseCase: ValideteEmailUseCase,
    val passwordUseCase: ValidetePasswordUseCase,
)