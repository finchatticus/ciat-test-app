package com.example.testapp.domain.source

sealed class Result<out T : Any> {

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(val exception: Throwable) : Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success -> "Result.Success: ${this.data}"
            is Error -> "Result.Error: ${this.exception}"
        }
    }
}

typealias MyResult<T> = Result<T>
typealias MySuccess<T> = Result.Success<T>
typealias MyError = Result.Error