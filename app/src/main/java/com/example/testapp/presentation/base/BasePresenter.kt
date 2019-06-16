package com.example.testapp.presentation.base

interface BasePresenter {

    fun start()

    fun viewDestroyed(changingConfigurations: Boolean)

}