package com.example.testapp.presentation.base

interface BaseView<T> {

    var presenter: T?

    fun showDebugMessage(message: String)

}