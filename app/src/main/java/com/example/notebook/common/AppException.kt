package com.example.notebook.common

open class AppException(message: String) : Exception(message) {}


class InvalideTodoException(message: String):AppException(message)

class InvalidNoteException(message: String):AppException(message)
